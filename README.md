# dastres
DASTRES is backend service to help mobile client app (GIS app)
I know ant and maven are build tools but at this time I do not use it. (I think have not enough time or maybe I am lazy at this time :-) )
The codes based on Java.
To improve search time and use free server on internet, try to simulate distributed processing and distribute storage.
Client can see only master app and use id to store and search request.
Master app distribute request to all slave app.
Slave app does not depend together.
You should deploy a version of slave on any location and set address of it in master configuration page.
Master has some page to define slave location and manage it.
Also has page to import data, see logs and monitoring slave status.
To improve of my knowledge, publish this code. 
Please help me to improve it and help to improve our idea.
