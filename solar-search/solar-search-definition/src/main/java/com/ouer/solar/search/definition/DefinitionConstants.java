/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.search.definition;


/**
 *
 * @author: <a href="indra@ixiaopu.com">chenxi</a>
 */

public abstract class DefinitionConstants {

//	public enum Definition {
//
//		INDEX_CONFIG_FILE("definition-index.xml"),
//		RESULT_CONFIG_FILE("definition-result.xml"),
//		SEARCH_CONFIG_FILE("definition-search.xml")
//	    ;
//
//	    private final String file;
//
//	    Definition(String file) {
//	    	this.file = file;
//	    }
//
//		public String file() {
//			return file;
//		}
//
//	}

	// definition configuration
//	public final static String INDEX_CONFIG_FILE = "definition-index.xml";
//	public final static String RESULT_CONFIG_FILE = "definition-result.xml";
//	public final static String SEARCH_CONFIG_FILE = "definition-search.xml";

    public final static String ROOT_EL_NAME = "definition";

    public final static String INDEXES_EL = "indexes";
    public final static String INDEX_EL = "index";
    public final static String RESULTS_EL = "results";
    public final static String RESULT_EL = "result";
//    public final static String SEARCHES_EL = "searches";
//    public final static String SEARCH_EL = "search";

    public final static String FIELDS_EL = "fields";
    public final static String SEARCH_FIELDS_EL = "searchfields";
    public final static String FILTER_FIELDS_EL = "filterfields";
    public final static String FIELD_EL = "field";

    public final static String SORTS_EL = "sorts";
    public final static String SORT_EL = "sort";

    public final static String AGGREGATIONS_EL = "aggregations";
    public final static String AGGREGATION_EL = "aggregation";

    public final static String SCORE_FIELD_EL = "scoreField";

    public final static String ID_ATTR = "id";
    public final static String INDEX_ATTR = "index";
    public final static String TYPE_ATTR = "type";
    public final static String NAME_ATTR = "name";
    public final static String PREINDEX_ATTR = "preIndex";
    public final static String ES_ID_ATTR = "_id";
    public final static String STORE_ATTR = "store";
    public final static String ORDER_ATTR = "order";

    public final static String INDEX_ID_ATTR = "indexId";
//    public final static String RESULT_ID_ATTR = "resultId";
    public final static String BOOLTYPE_ATTR = "boolType";
    public final static String QUERYTYPE_ATTR = "queryType";
    public final static String BOOST_ATTR = "boost";
    public final static String MULTITYPE_ATTR = "multiType";
    public final static String FILTERTYPE_ATTR = "filterType";
    public final static String CONSTANTSCORE_ATTR = "constantScore";

    public final static String FIELD_ATTR = "field";

    public final static String FACTOR_ATTR = "factor";
    public final static String MODIFIER_ATTR = "modifier";

    // search parameters
//    public final static String PARAM_INDEX_ID_KEY = "INDEX_ID";
//    public final static String PARAM_RESULT_ID_KEY = "RESULT_ID";
//    public final static String PARAM_SEARCH_ID_KEY = "SEARCH_ID";
//    public final static String PARAM_PAGE = "PAGE";
//    public final static String PARAM_PAGESIZE = "PAGESIZE";

    // notify type
    public static final int NOTIFY_DELETE_KEY = 1;
    public static final int NOTIFY_UPDATE_KEY = 2;
}
