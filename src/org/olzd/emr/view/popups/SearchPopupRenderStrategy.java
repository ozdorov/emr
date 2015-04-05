package org.olzd.emr.view.popups;

public enum SearchPopupRenderStrategy {
    SEARCH_BY_NAME {
        @Override
        public String getCriteriaFieldLabel() {
            return "Фамилия";
        }
    },
    SEARCH_BY_DIAGNOSIS {
        @Override
        public String getCriteriaFieldLabel() {
            return "Диагноз";
        }
    };

    public abstract String getCriteriaFieldLabel();

}
