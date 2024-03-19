CREATE TABLE quad_tree_node (
    id BIGINT NOT NULL,
    left_top_lat DECIMAL(8,6) NOT NULL,
    left_top_lon DECIMAL(9,6) NOT NULL,
    right_bot_lat DECIMAL(8,6) NOT NULL,
    right_bot_lon DECIMAL(9,6) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE quad_tree_node_inheritance (
    id_parent BIGINT NOT NULL,
    id_child BIGINT NOT NULL,
    PRIMARY KEY (id_parent, id_child)
);

CREATE TABLE quad_tree_node_adjacency (
    id_1 BIGINT NOT NULL,
    id_2 BIGINT NOT NULL,
    PRIMARY KEY (id_1, id_2)
);

CREATE TABLE quad_tree_node_load (
    id BIGINT NOT NULL,
    expected_factor INTEGER DEFAULT 0
);
