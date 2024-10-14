-- Rejex statement ensuring a minimum of 7 characters and a maximum of 20, with one number and one special character
SELECT * FROM users
WHERE password REGEXP '^(?=.*[0-9])(?=.*[\W]).{7,20}$'; -- filler column

-- QUERIES FOR QUESTIONS
-- QUESTION 1: Is this plant a tree?

SELECT * -- answer yes
FROM Tree
WHERE PlantType_ID = 1;

SELECT * -- answer no
FROM Tree
WHERE PlantType_ID = 2;

-- Question 2: Is the leaf scale, needle or broad?

SELECT * -- scale
FROM Tree
WHERE PlantType_ID = 1 AND LeafType_ID = 1;

SELECT * -- needle
FROM Tree
WHERE PlantType_ID = 1 AND LeafType_ID = 2;

SELECT * -- broad
FROM Tree
WHERE PlantType_ID = 1 AND LeafType_ID = 3;

-- Question 3: Does the leaf edge have teeth or is the edge smooth?

SELECT * -- teeth edge -> Question 4/5
FROM Tree
WHERE PlantType_ID = 1 AND LeafType_ID = 3 AND (ID = 3 OR ID = 4); -- narrowing to Red Maple or Sweetgum

SELECT * -- smooth edge -> Question 6/7
FROM Tree
WHERE PlantType_ID = 1 AND LeafType_ID = 3 AND (ID = 5 OR ID = 6); -- narrowing to White Oak or Blackjack Oak

-- Question 4: Does the plant have paired samaras and red petiole?

SELECT *
FROM Tree
WHERE PlantType_ID = 1 AND LeafType_ID = 3 AND ID = 3; -- RED MAPLE

-- Question 5: Is the leaf star-shaped with a spined, round capsule?

SELECT *
FROM Tree
WHERE PlantType_ID = 1 AND LeafType_ID = 3 AND ID = 4; -- SWEETGUM

-- Question 6:

-- Question 7: