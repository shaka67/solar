/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.definition;

import static com.ouer.solar.search.definition.DefinitionConstants.AGGREGATIONS_EL;
import static com.ouer.solar.search.definition.DefinitionConstants.AGGREGATION_EL;
import static com.ouer.solar.search.definition.DefinitionConstants.BOOLTYPE_ATTR;
import static com.ouer.solar.search.definition.DefinitionConstants.BOOST_ATTR;
import static com.ouer.solar.search.definition.DefinitionConstants.CONSTANTSCORE_ATTR;
import static com.ouer.solar.search.definition.DefinitionConstants.ES_ID_ATTR;
import static com.ouer.solar.search.definition.DefinitionConstants.FACTOR_ATTR;
import static com.ouer.solar.search.definition.DefinitionConstants.FIELDS_EL;
import static com.ouer.solar.search.definition.DefinitionConstants.FIELD_ATTR;
import static com.ouer.solar.search.definition.DefinitionConstants.FIELD_EL;
import static com.ouer.solar.search.definition.DefinitionConstants.FILTERTYPE_ATTR;
import static com.ouer.solar.search.definition.DefinitionConstants.FILTER_FIELDS_EL;
import static com.ouer.solar.search.definition.DefinitionConstants.ID_ATTR;
import static com.ouer.solar.search.definition.DefinitionConstants.INDEXES_EL;
import static com.ouer.solar.search.definition.DefinitionConstants.INDEX_ATTR;
import static com.ouer.solar.search.definition.DefinitionConstants.INDEX_EL;
import static com.ouer.solar.search.definition.DefinitionConstants.INDEX_ID_ATTR;
import static com.ouer.solar.search.definition.DefinitionConstants.MODIFIER_ATTR;
import static com.ouer.solar.search.definition.DefinitionConstants.MULTITYPE_ATTR;
import static com.ouer.solar.search.definition.DefinitionConstants.NAME_ATTR;
import static com.ouer.solar.search.definition.DefinitionConstants.ORDER_ATTR;
import static com.ouer.solar.search.definition.DefinitionConstants.PREINDEX_ATTR;
import static com.ouer.solar.search.definition.DefinitionConstants.QUERYTYPE_ATTR;
import static com.ouer.solar.search.definition.DefinitionConstants.RESULTS_EL;
import static com.ouer.solar.search.definition.DefinitionConstants.RESULT_EL;
import static com.ouer.solar.search.definition.DefinitionConstants.ROOT_EL_NAME;
import static com.ouer.solar.search.definition.DefinitionConstants.SCORE_FIELD_EL;
import static com.ouer.solar.search.definition.DefinitionConstants.SEARCH_FIELDS_EL;
import static com.ouer.solar.search.definition.DefinitionConstants.SORTS_EL;
import static com.ouer.solar.search.definition.DefinitionConstants.SORT_EL;
import static com.ouer.solar.search.definition.DefinitionConstants.STORE_ATTR;
import static com.ouer.solar.search.definition.DefinitionConstants.TYPE_ATTR;

import java.net.URL;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration.Node;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ouer.solar.CollectionUtil;
import com.ouer.solar.search.api.AggregationDefinition;
import com.ouer.solar.search.api.BoolType;
import com.ouer.solar.search.api.FilterField;
import com.ouer.solar.search.api.FilterType;
import com.ouer.solar.search.api.QueryType;
import com.ouer.solar.search.api.ScoreField;
import com.ouer.solar.search.api.SearchField;
import com.ouer.solar.search.api.SortField;

/**
 *
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class DefinitionFactoryBuilder {

	private static final Logger LOG = LoggerFactory.getLogger(DefinitionFactoryBuilder.class);

	public static DefinitionFactory buildFactory(URL index, URL result, URL search) throws DefinitionException {
		final DefinitionFactory factory = new DefinitionFactory();
		buildIndexes(factory, index);
		buildResults(factory, result);
		buildSearches(factory, search);
		return factory;
	}

	public static void buildIndexes(DefinitionFactory factory, URL url) throws DefinitionException {
		final XMLConfiguration configuration = new XMLConfiguration();
        configuration.setAttributeSplittingDisabled(true);
		try {
			configuration.load(url);
			buildIndexes(factory, configuration);
		} catch (final ConfigurationException e) {
			throw new DefinitionException(e);
		}
	}

	public static void buildResults(DefinitionFactory factory, URL url) throws DefinitionException {
		final XMLConfiguration configuration = new XMLConfiguration();
        configuration.setAttributeSplittingDisabled(true);
		try {
			configuration.load(url);
			buildResults(factory, configuration);
		} catch (final ConfigurationException e) {
			throw new DefinitionException(e);
		}
	}

	public static void buildSearches(DefinitionFactory factory, URL url) throws DefinitionException {
		final XMLConfiguration configuration = new XMLConfiguration();
        configuration.setAttributeSplittingDisabled(true);
		try {
			configuration.load(url);
			buildSearches(factory, configuration);
		} catch (final ConfigurationException e) {
			throw new DefinitionException(e);
		}
	}

    private static boolean buildIndexes(DefinitionFactory factory, XMLConfiguration configuration) throws DefinitionException {
    	final Node root = configuration.getRoot();
        if (root == null || !ROOT_EL_NAME.equals(root.getName())) {
            throw new DefinitionException("invalid root element!");
        }

        final List<ConfigurationNode> nodes = root.getChildren(INDEXES_EL);
        if (nodes == null || nodes.size() == 0) {
            LOG.warn("no indexes element found, cannot continue more.");
            return false;
        }
        if (nodes.size() != 1) {
            throw new DefinitionException("more than one indexes elements found!");
        }

        final List<ConfigurationNode> indexes = nodes.get(0).getChildren(INDEX_EL);
        if (indexes == null || indexes.size() == 0) {
            LOG.warn("no index elements found, cannot continue more.");
            return false;
        }

        IndexClass indexClass;
        IndexField field;
        int id;
        String index;
        String type;
        String name;
        boolean preIndex;

        List<ConfigurationNode> fields;
        String fieldName;
        String fieldType;
        IndexType indexType;
        StoreType storeType;

        for (final ConfigurationNode node : indexes) {
        	id = Integer.valueOf(getAttribute(node, ID_ATTR));
        	index = getAttribute(node, INDEX_ATTR);
            type = getAttribute(node, TYPE_ATTR);
            name = getAttribute(node, NAME_ATTR);
            preIndex = false;
            if (getAttribute(node, PREINDEX_ATTR) != null) {
				preIndex = Boolean.valueOf(getAttribute(node, PREINDEX_ATTR));
			}
            indexClass = new IndexClass(id, index, type, name, preIndex);
            if (getAttribute(node, ES_ID_ATTR) != null) {
            	indexClass.setEsId(getAttribute(node, ES_ID_ATTR));
            }
            fields = node.getChildren(FIELDS_EL);
            if (fields == null) {
                LOG.warn("no fields element found, cannot continue more.");
                return false;
            }
            if (fields.size() != 1) {
                throw new DefinitionException("more than one fields elements found!");
            }
            final List<ConfigurationNode> fieldList = fields.get(0).getChildren(FIELD_EL);
            if (fieldList == null || fieldList.size() == 0) {
                LOG.warn("no field elements found, cannot continue more.");
                return false;
            }
            for (final ConfigurationNode fieldNode : fieldList) {
                fieldName = getAttribute(fieldNode, NAME_ATTR);
                field = new IndexField(fieldName);
                if (preIndex) {
                	fieldType = "string";
                    indexType = IndexType.ANALYZED;
                    storeType = StoreType.NO;
                    if (getAttribute(fieldNode, TYPE_ATTR) != null) {
                    	fieldType = getAttribute(fieldNode, TYPE_ATTR);
                    }
                    if (getAttribute(fieldNode, INDEX_ATTR) != null) {
                    	indexType = IndexType.fromDesc(getAttribute(fieldNode, INDEX_ATTR));
                    }
                    if (getAttribute(fieldNode, STORE_ATTR) != null) {
                    	storeType = StoreType.fromDesc(getAttribute(fieldNode, STORE_ATTR));
                    }
                    field.setType(fieldType);
                    field.setIndexType(indexType);
                    field.setStoreType(storeType);
                }
                indexClass.addField(field);
            }
            factory.addIndexClass(indexClass);
        }
        configuration.clear();
        return true;
    }

    private static boolean buildResults(DefinitionFactory factory, XMLConfiguration configuration) throws DefinitionException {
    	final Node root = configuration.getRoot();
        if (root == null || !ROOT_EL_NAME.equals(root.getName())) {
            throw new DefinitionException("invalid root element!");
        }

        final List<ConfigurationNode> nodes = root.getChildren(RESULTS_EL);
        if (nodes == null || nodes.size() == 0) {
            LOG.warn("no results element found, cannot continue more.");
            return false;
        }
        if (nodes.size() != 1) {
            throw new DefinitionException("more than one results elements found!");
        }

        final List<ConfigurationNode> results = nodes.get(0).getChildren(RESULT_EL);
        if (results == null || results.size() == 0) {
            LOG.warn("no result elements found, cannot continue more.");
            return false;
        }

        ResultClass result;
        ResultField field;
        int id;
        int indexId;
        String name;
        List<ConfigurationNode> fields;
        String fieldName;

        for (final ConfigurationNode node : results) {
        	id = Integer.valueOf(getAttribute(node, ID_ATTR));
        	indexId = Integer.valueOf(getAttribute(node, INDEX_ID_ATTR));
        	if (factory.getIndexClass(indexId) == null) {
				throw new DefinitionException("cannot find index: [" + indexId + "] in factory");
			}

            name = getAttribute(node, NAME_ATTR);
            result = new ResultClass(id, indexId, name);
            fields = node.getChildren(FIELDS_EL);
            if (fields == null) {
                LOG.warn("no fields element found, cannot continue more.");
                return false;
            }
            if (fields.size() != 1) {
                throw new DefinitionException("more than one fields elements found!");
            }
            final List<ConfigurationNode> fieldList = fields.get(0).getChildren(FIELD_EL);
            if (fieldList == null || fieldList.size() == 0) {
                LOG.warn("no field elements found, cannot continue more.");
                return false;
            }
            for (final ConfigurationNode fieldNode : fieldList) {
                fieldName = getAttribute(fieldNode, NAME_ATTR);
                field = new ResultField(fieldName);
                result.addField(field);
            }
            factory.addResultClass(result);
        }
        configuration.clear();
        return true;
    }

    private static boolean buildSearches(DefinitionFactory factory, XMLConfiguration configuration) throws DefinitionException {
    	final Node root = configuration.getRoot();
        if (root == null || !ROOT_EL_NAME.equals(root.getName())) {
            throw new DefinitionException("invalid root element!");
        }

        buildSearchFields(factory, root);
        buildFilterFields(factory, root);
        buildSorts(factory, root);
        buildAggregations(factory, root);
        buildScoreFields(factory, root);

        configuration.clear();
        return true;
    }

    private static boolean buildSearchFields(DefinitionFactory factory, ConfigurationNode node) throws DefinitionException {
    	final List<ConfigurationNode> searchFields = node.getChildren(SEARCH_FIELDS_EL);
    	if (CollectionUtil.isEmpty(searchFields)) {
            LOG.warn("no searchfields element found, cannot continue more.");
            return false;
        }

        for (final ConfigurationNode searchFieldsNode : searchFields) {
        	final List<ConfigurationNode> fieldList = searchFieldsNode.getChildren(FIELD_EL);
            if (fieldList == null || fieldList.size() == 0) {
                throw new DefinitionException("no field elements found, cannot continue more.");
            }
            final int searchFieldsId = Integer.valueOf(getAttribute(searchFieldsNode, ID_ATTR));

            String fieldName;
            String fieldBool;
            BoolType boolType;
            String fieldQuery;
            QueryType queryType;
            String fieldBoost;
            float boost;
            BoolType multiType;
            boolean constantScore = false;

            SearchField field;
            for (final ConfigurationNode fieldNode : fieldList) {
                fieldName = getAttribute(fieldNode, NAME_ATTR);
                fieldBool = getAttribute(fieldNode, BOOLTYPE_ATTR);
                if (fieldBool == null) {
                	boolType = BoolType.SHOULD;
    			} else {
    				boolType = BoolType.fromDesc(fieldBool);
    			}
                fieldQuery = getAttribute(fieldNode, QUERYTYPE_ATTR);
                if (fieldQuery == null) {
                	queryType = QueryType.MATCH;
    			} else {
    				queryType = QueryType.fromDesc(fieldQuery);
    			}
                if (getAttribute(fieldNode, CONSTANTSCORE_ATTR) != null) {
                	constantScore = Boolean.valueOf(getAttribute(fieldNode, CONSTANTSCORE_ATTR));
                }
                fieldBoost = getAttribute(fieldNode, BOOST_ATTR);
                if (fieldBoost == null) {
                	boost = 1.0f;
    			} else {
    				boost = Float.valueOf(fieldBoost);
    			}
                multiType = BoolType.SHOULD;
                if (getAttribute(fieldNode, MULTITYPE_ATTR) != null) {
                	multiType = BoolType.fromDesc(getAttribute(fieldNode, MULTITYPE_ATTR));
                }
                field = new SearchField(fieldName, boolType, queryType, boost, multiType, constantScore);
                factory.addSearchField(searchFieldsId, field);
            }
        }

        return true;
    }

    private static boolean buildFilterFields(DefinitionFactory factory, ConfigurationNode node) throws DefinitionException {
    	final List<ConfigurationNode> filterFields = node.getChildren(FILTER_FIELDS_EL);
    	if (CollectionUtil.isEmpty(filterFields)) {
            LOG.warn("no filterfields element found, cannot continue more.");
            return false;
        }

        for (final ConfigurationNode filterFieldsNode : filterFields) {
        	final List<ConfigurationNode> fieldList = filterFieldsNode.getChildren(FIELD_EL);
            if (fieldList == null || fieldList.size() == 0) {
                throw new DefinitionException("no field elements found, cannot continue more.");
            }
            final int filterFieldsId = Integer.valueOf(getAttribute(filterFieldsNode, ID_ATTR));

            String fieldName;
            FilterType filterType;

            FilterField field;
            for (final ConfigurationNode fieldNode : fieldList) {
                fieldName = getAttribute(fieldNode, NAME_ATTR);
                filterType = FilterType.fromDesc(getAttribute(fieldNode, FILTERTYPE_ATTR));
                field = new FilterField(fieldName, filterType);
                factory.addFilterField(filterFieldsId, field);
            }
        }

        return true;
    }

    private static boolean buildSorts(DefinitionFactory factory, ConfigurationNode node) throws DefinitionException {
    	final List<ConfigurationNode> sorts = node.getChildren(SORTS_EL);
        if (CollectionUtil.isEmpty(sorts)) {
            LOG.warn("no sorts element found, cannot continue more.");
            return false;
        }

        for (final ConfigurationNode sortsNode : sorts) {
        	final List<ConfigurationNode> sortList = sortsNode.getChildren(SORT_EL);
            if (sortList == null || sortList.size() == 0) {
            	throw new DefinitionException("no sort elements found, cannot continue more.");
            }
            final int sortsId = Integer.valueOf(getAttribute(sortsNode, ID_ATTR));

            String sortName;
            SortField.Order sortOrder;
            for (final ConfigurationNode sortNode : sortList) {
            	sortName = getAttribute(sortNode, NAME_ATTR);
            	sortOrder = SortField.Order.fromType(getAttribute(sortNode, ORDER_ATTR));
            	factory.addSortField(sortsId, new SortField(sortName, sortOrder));
            }
        }

        return true;
    }

    private static boolean buildAggregations(DefinitionFactory factory, ConfigurationNode node) throws DefinitionException {
    	final List<ConfigurationNode> aggregations = node.getChildren(AGGREGATIONS_EL);
    	if (CollectionUtil.isEmpty(aggregations)) {
            LOG.warn("no aggregations element found, cannot continue more.");
            return false;
        }

    	for (final ConfigurationNode aggsNode : aggregations) {
    		final List<ConfigurationNode> aggregationList = aggsNode.getChildren(AGGREGATION_EL);
            if (aggregationList == null || aggregationList.size() == 0) {
            	throw new DefinitionException("no aggregation elements found, cannot continue more.");
            }
            final int aggsId = Integer.valueOf(getAttribute(aggsNode, ID_ATTR));

            String type;
            String name;
            String field;
            for (final ConfigurationNode aggregation : aggregationList) {
            	type = getAttribute(aggregation, TYPE_ATTR);
            	name = getAttribute(aggregation, NAME_ATTR);
            	field = getAttribute(aggregation, FIELD_ATTR);
            	factory.addAggregation(aggsId, new AggregationDefinition(type, name, field));
            }
    	}

        return true;
    }

    private static Boolean buildScoreFields(DefinitionFactory factory, Node node) throws DefinitionException {
    	final List<ConfigurationNode> scoreFields = node.getChildren(SCORE_FIELD_EL);
    	if (CollectionUtil.isEmpty(scoreFields)) {
            LOG.warn("no scoreField element found, cannot continue more.");
            return false;
        }

    	String id;
    	String name;
    	String factor;
    	String modifier;
    	ScoreField scoreField;
    	for (final ConfigurationNode cNode : scoreFields) {
    		id = getAttribute(cNode, ID_ATTR);
    		name = getAttribute(cNode, NAME_ATTR);
        	factor = getAttribute(cNode, FACTOR_ATTR);
        	modifier = getAttribute(cNode, MODIFIER_ATTR);
        	scoreField = new ScoreField(name, Float.valueOf(factor), modifier);
        	factory.addScoreField(Integer.valueOf(id), scoreField);
    	}
        return true;
	}

    private static String getAttribute(ConfigurationNode node, String attr) throws DefinitionException {
        final List<ConfigurationNode> nodes = node.getAttributes(attr);
        if (nodes == null || nodes.size() == 0) {
            return null;
        }

        if (nodes.size() > 1) {
            throw new DefinitionException("duplicate attributes [" + attr + "] in element [" + node.getName() + "]");
        }

        return (String) nodes.get(0).getValue();
    }

}
