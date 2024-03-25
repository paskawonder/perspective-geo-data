CREATE TABLE geo_tree_node (
    id CHAR(36) NOT NULL,
    left_top_lat DECIMAL(8,6) NOT NULL,
    left_top_lon DECIMAL(9,6) NOT NULL,
    right_bot_lat DECIMAL(8,6) NOT NULL,
    right_bot_lon DECIMAL(9,6) NOT NULL,
    parent_id CHAR(36),
    PRIMARY KEY (id)
);

CREATE TABLE geo_tree_node_adjacency (
    id_1 CHAR(36) NOT NULL,
    id_2 CHAR(36) NOT NULL,
    PRIMARY KEY (id_1, id_2)
);

CREATE TABLE media_info (
    id CHAR(36) NOT NULL,
    lat DECIMAL(8,6) NOT NULL,
    lon DECIMAL(9,6) NOT NULL,
    geo_leaf_id CHAR(36) NOT NULL,
    PRIMARY KEY (id)
);