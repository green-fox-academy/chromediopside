ALTER TABLE language
ADD file_extension VARCHAR(255);

UPDATE language
SET file_extension = '.java'
WHERE language_name = 'Java';

UPDATE language
SET file_extension = '.html'
WHERE language_name = 'HTML';

UPDATE language
SET file_extension = '.css'
WHERE language_name = 'CSS';

UPDATE language
SET file_extension = '.cs'
WHERE language_name = 'C#';

UPDATE language
SET file_extension = '.cc'
WHERE language_name = 'C++';

UPDATE language
SET file_extension = '.php'
WHERE language_name = 'PHP';

UPDATE language
SET file_extension = '.py'
WHERE language_name = 'Python';

UPDATE language
SET file_extension = '.js'
WHERE language_name = 'JavaScript';

UPDATE language
SET file_extension = '.c'
WHERE language_name = 'C';
