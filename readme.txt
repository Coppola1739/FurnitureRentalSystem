Sample username and password: 
Employee: bobmiller,password4
Admin: charliebrown,password5

Project is a Java Maven project.
To get dependency working in eclipse, run this in the terminal:
"java -jar lib/lombok-1.18.30.jar"
Login screen validates username and password and also hides password field. It removes any trailing white space.
After pressing submit, it checks the DB's user table for a matching username and password and then either returns success or fail.
