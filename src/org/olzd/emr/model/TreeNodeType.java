package org.olzd.emr.model;

public enum TreeNodeType {
    CARDNODE {
        @Override
        public TreeNodeType getTypeOfParent() {
            return null;
        }

        @Override
        public TreeNodeType getChildNodesType() {
            return null;
        }
    },
    EXAMINATION_SHEET {
        @Override
        public TreeNodeType getTypeOfParent() {
            return EXAMINATION_PLACEHOLDER;
        }

        @Override
        public TreeNodeType getChildNodesType() {
            return null;
        }
    },
    ANALYSIS_PLACEHOLDER {
        @Override
        public TreeNodeType getTypeOfParent() {
            return null;
        }

        @Override
        public TreeNodeType getChildNodesType() {
            return ANALYSIS_TYPE;
        }
    },
    SURGERY_PLACEHOLDER {
        @Override
        public TreeNodeType getTypeOfParent() {
            return null;
        }

        @Override
        public TreeNodeType getChildNodesType() {
            return SURGERY_FILE;
        }
    },
    EXAMINATION_PLACEHOLDER {
        @Override
        public TreeNodeType getTypeOfParent() {
            return null;
        }

        @Override
        public TreeNodeType getChildNodesType() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
    },
    TECH_EXAMINATION_PLACEHOLDER {
        @Override
        public TreeNodeType getTypeOfParent() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public TreeNodeType getChildNodesType() {
            return TECH_EXAMINATION_TYPE;
        }
    },
    TECH_EXAMINATION_TYPE {
        @Override
        public TreeNodeType getTypeOfParent() {
            return TECH_EXAMINATION_PLACEHOLDER;
        }

        @Override
        public TreeNodeType getChildNodesType() {
            return TECH_EXAMINATION_FILE;
        }
    },
    TECH_EXAMINATION_FILE {
        @Override
        public TreeNodeType getTypeOfParent() {
            return TECH_EXAMINATION_FILE;
        }

        @Override
        public TreeNodeType getChildNodesType() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
    },
    ANALYSIS_TYPE {
        @Override
        public TreeNodeType getTypeOfParent() {
            return ANALYSIS_PLACEHOLDER;
        }

        @Override
        public TreeNodeType getChildNodesType() {
            return ANALYSIS_FILE;
        }
    },
    ANALYSIS_FILE {
        @Override
        public TreeNodeType getTypeOfParent() {
            return ANALYSIS_TYPE;
        }

        @Override
        public TreeNodeType getChildNodesType() {
            return null;
        }
    },
    SURGERY_FILE {
        @Override
        public TreeNodeType getTypeOfParent() {
            return SURGERY_PLACEHOLDER;
        }

        @Override
        public TreeNodeType getChildNodesType() {
            return null;
        }
    },
    ROOT {
        @Override
        public TreeNodeType getTypeOfParent() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public TreeNodeType getChildNodesType() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
    };

    public abstract TreeNodeType getTypeOfParent();

    public abstract TreeNodeType getChildNodesType();

}
