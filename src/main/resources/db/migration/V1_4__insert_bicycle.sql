INSERT INTO bicycles
(bicycle_id, model, price, components, manufacturer_id, number_of_wheels, invoice_id)
VALUES
(
gen_random_uuid(),
'Model 1',
99.99,
'{"component 1", "component 2"}',
(SELECT manufacturer_id FROM bicycle_manufacturers WHERE name='AAA'),
2,
NULL
);