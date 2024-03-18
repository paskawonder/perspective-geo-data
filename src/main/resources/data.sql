CREATE TABLE RectTreeNode (
    id bigint unsigned NOT NULL,
    left_top_lat double NOT NULL,
    left_top_lon double NOT NULL,
    right_bot_lat double NOT NULL,
    right_bot_lon double NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE RectTreeNodeInheritance (
    id_parent bigint unsigned NOT NULL,
    id_child bigint unsigned NOT NULL,
    PRIMARY KEY (id_parent, id_child)
);

CREATE TABLE RectTreeNodeAdjacency (
    id_1 bigint unsigned NOT NULL,
    id_2 bigint unsigned NOT NULL,
    PRIMARY KEY (id_1, id_2)
);

CREATE TABLE RectTreeNodeLoad (
    id bigint unsigned NOT NULL,
    expected_loading_factor tinyint unsigned
);

INSERT INTO RectTreeNode (id, left_top_lat, left_top_lon, right_bot_lat, right_bot_lon)
VALUES
(1, 90, -180, -90, 180),
(2, 90, -180, -60, -30),
(3, 90, -30, 30, 180),
(4, 30, -30, -60, 180),
(5, -60, -180, -90, 180)

INSERT INTO RectTreeNodeInheritance (id_parent, id_child)
VALUES
(1, 2),
(1, 3),
(1, 4),
(1, 5)

INSERT INTO RectTreeNodeAdjacency (id_1, id_2)
VALUES
(2, 3),
(2, 4),
(2, 5),
(3, 4),
(3, 5)
(4, 5)

INSERT INTO RectTreeNodeLoad (id, expected_loading_factor)
VALUES
(1, 0),
(2, 10),
(3, 10),
(4, 0),
(5, 0)
(6, 0)
