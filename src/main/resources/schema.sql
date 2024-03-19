CREATE TABLE quad_tree_node (
    id CHAR(36) NOT NULL,
    left_top_lat DECIMAL(8,6) NOT NULL,
    left_top_lon DECIMAL(9,6) NOT NULL,
    right_bot_lat DECIMAL(8,6) NOT NULL,
    right_bot_lon DECIMAL(9,6) NOT NULL,
    load_factor INTEGER NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE quad_tree_node_inheritance (
    id_parent CHAR(36) NOT NULL,
    id_child CHAR(36) NOT NULL,
    PRIMARY KEY (id_parent, id_child)
);

CREATE TABLE quad_tree_node_adjacency (
    id_1 CHAR(36) NOT NULL,
    id_2 CHAR(36) NOT NULL,
    PRIMARY KEY (id_1, id_2)
);
