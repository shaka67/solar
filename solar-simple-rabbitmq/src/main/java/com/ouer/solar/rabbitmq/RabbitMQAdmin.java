/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.rabbitmq;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.util.Assert;

import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.Channel;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public class RabbitMQAdmin implements AmqpAdmin {

	private static final Object QUEUE_NAME = "QUEUE_NAME";
	private static final Object QUEUE_MESSAGE_COUNT = "QUEUE_MESSAGE_COUNT";
	private static final Object QUEUE_CONSUMER_COUNT = "QUEUE_CONSUMER_COUNT";

	private final static Log LOG = LogFactory.getLog(RabbitMQAdmin.class);
    private final RabbitTemplate template;

    public RabbitMQAdmin(RabbitTemplate template) {
    	this.template = template;
    }

	@Override
	public void declareExchange(final Exchange exchange) {
		this.template.execute(new ChannelCallback<Object>() {
            @Override
            public Object doInRabbit(Channel channel) throws Exception {
                declareExchanges(channel, exchange);
                return null;
            }
        });
	}

	@Override
	public boolean deleteExchange(final String exchangeName) {
		return this.template.execute(new ChannelCallback<Boolean>() {
            @Override
            public Boolean doInRabbit(Channel channel) throws Exception {
                try {
                    channel.exchangeDelete(exchangeName);
                } catch (final IOException e) {
                    return false;
                }
                return true;
            }
        });
	}

	@Override
	public Queue declareQueue() {
		final DeclareOk declareOk = this.template.execute(new ChannelCallback<DeclareOk>() {
            @Override
            public DeclareOk doInRabbit(Channel channel) throws Exception {
                return channel.queueDeclare();
            }
        });
        final Queue queue = new Queue(declareOk.getQueue(), false, true, true);
        return queue;
	}

	@Override
	public String declareQueue(final Queue queue) {
		return this.template.execute(new ChannelCallback<String>() {
			@Override
			public String doInRabbit(Channel channel) throws Exception {
				return declareQueues(channel, queue)[0].getQueue();
			}
		});
	}

	@Override
	public boolean deleteQueue(final String queueName) {
		return this.template.execute(new ChannelCallback<Boolean>() {
            @Override
            public Boolean doInRabbit(Channel channel) throws Exception {
                try {
                    channel.queueDelete(queueName);
                } catch (final IOException e) {
                    return false;
                }
                return true;
            }
        });
	}

	@Override
	public void deleteQueue(final String queueName, final boolean unused, final boolean empty) {
		this.template.execute(new ChannelCallback<Object>() {
            @Override
            public Object doInRabbit(Channel channel) throws Exception {
                channel.queueDelete(queueName, unused, empty);
                return null;
            }
        });
	}

	@Override
	public void purgeQueue(final String queueName, boolean noWait) {
		this.template.execute(new ChannelCallback<Object>() {
            @Override
            public Object doInRabbit(Channel channel) throws Exception {
                channel.queuePurge(queueName);
                return null;
            }
        });
	}

	@Override
	public void declareBinding(final Binding binding) {
		this.template.execute(new ChannelCallback<Object>() {
            @Override
            public Object doInRabbit(Channel channel) throws Exception {
                declareBindings(channel, binding);
                return null;
            }
        });
	}

	@Override
	public void removeBinding(final Binding binding) {
		template.execute(new ChannelCallback<Object>() {
            @Override
            public Object doInRabbit(Channel channel) throws Exception {
                if (binding.isDestinationQueue()) {
                    channel.queueUnbind(binding.getDestination(), binding.getExchange(), binding.getRoutingKey(),
                            binding.getArguments());
                } else {
                    channel.exchangeUnbind(binding.getDestination(), binding.getExchange(), binding.getRoutingKey(),
                            binding.getArguments());
                }
                return null;
            }
        });
	}

	@Override
	public Properties getQueueProperties(final String queueName) {
		Assert.hasText(queueName, "'queueName' cannot be null or empty");
		return this.template.execute(new ChannelCallback<Properties>() {
			@Override
			public Properties doInRabbit(Channel channel) throws Exception {
				try {
					final DeclareOk declareOk = channel.queueDeclarePassive(queueName);
					final Properties props = new Properties();
					props.put(QUEUE_NAME, declareOk.getQueue());
					props.put(QUEUE_MESSAGE_COUNT, declareOk.getMessageCount());
					props.put(QUEUE_CONSUMER_COUNT, declareOk.getConsumerCount());
					return props;
				}
				catch (final Exception e) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("Queue '" + queueName + "' does not exist");
					}
					return null;
				}
			}
		});
	}

	private void declareExchanges(final Channel channel, final Exchange... exchanges) throws IOException {
        for (final Exchange exchange : exchanges) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("declaring Exchange '" + exchange.getName() + "'");
            }
            channel.exchangeDeclare(exchange.getName(), exchange.getType(), exchange.isDurable(),
                    exchange.isAutoDelete(), exchange.getArguments());
        }
    }

	private DeclareOk[] declareQueues(final Channel channel, final Queue... queues) throws IOException {
		final DeclareOk[] declareOks = new DeclareOk[queues.length];
		for (int i = 0; i < queues.length; i++) {
			final Queue queue = queues[i];
			if (!queue.getName().startsWith("amq.")) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("declaring Queue '" + queue.getName() + "'");
				}

				final DeclareOk declareOk = channel.queueDeclare(queue.getName(), queue.isDurable(), queue.isExclusive(), queue.isAutoDelete(),
							queue.getArguments());
				declareOks[i] = declareOk;
			} else if (LOG.isDebugEnabled()) {
				LOG.debug("Queue with name that starts with 'amq.' cannot be declared.");
			}
		}
		return declareOks;
	}

    private void declareBindings(final Channel channel, final Binding... bindings) throws IOException {
        for (final Binding binding : bindings) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Binding destination [" + binding.getDestination() + " (" + binding.getDestinationType()
                        + ")] to exchange [" + binding.getExchange() + "] with routing key [" + binding.getRoutingKey()
                        + "]");
            }
            if (binding.isDestinationQueue()) {
                channel.queueBind(binding.getDestination(), binding.getExchange(), binding.getRoutingKey(),
                        binding.getArguments());
            } else {
                channel.exchangeBind(binding.getDestination(), binding.getExchange(), binding.getRoutingKey(),
                        binding.getArguments());
            }
        }
    }

    public RabbitTemplate getTemplate() {
    	return template;
    }
}
