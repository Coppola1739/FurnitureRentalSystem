Sample username and password: 
Nick , Password
Larry ,	12345
Requis ,	12313

Current issues
When a user is created they are assigned as a customer but they are not added to the member table.

Project is a Java Maven project.
To get dependency working in eclipse, run this in the terminal:
"java -jar lib/lombok-1.18.30.jar"
Login screen validates username and password and also hides password field. It removes any trailing white space.
After pressing submit, it checks the DB's user table for a matching username and password and then either returns success or fail.
