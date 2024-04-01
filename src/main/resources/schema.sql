CREATE TABLE geo_tree_node (
    id VARCHAR(36) NOT NULL,
    left_top_lat DECIMAL(8,6) NOT NULL,
    left_top_lng DECIMAL(9,6) NOT NULL,
    right_bot_lat DECIMAL(8,6) NOT NULL,
    right_bot_lng DECIMAL(9,6) NOT NULL,
    parent_id VARCHAR(36),
    PRIMARY KEY (id)
);

CREATE TABLE geo_tree_node_adjacency (
    id_1 VARCHAR(36) NOT NULL,
    id_2 VARCHAR(36) NOT NULL,
    PRIMARY KEY (id_1, id_2)
);
