# ExploringMarsSolution

This API allows the user to control "Sondas" in Mars' surface. The endpoints are explained below.

`POST`  /mars/setup
----
  Sets up the upper-right boundary of Mars' surface. Returns json data about the set boundary.

*  **Request Headers**
     <br /> Content-type: application/json <br />
     Accept: application/json
  
* **Data Params**

  `{"x": [integer],"y": [integer]}`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{"x": 5,"y": 5}`
 
* **Error Response:**

  * **Code:** 500 INTERNAL SERVER ERROR <br />
    **Content:** `{ error : "The surface's upper-right border was already set!" }`

  OR

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ error : "Not an Integer value!" }`

* **Sample Call:**

  ```curl
    curl 
		-H "Accept: application/json" 
		-H "Content-type: application/json" 
		-X POST -d '{"x": 5,"y": 5}' http://localhost:8080/mars/setup
  ```
  
<br />

`GET`  /mars/getsetup
----
  Returns json data about the set boundary.

  
* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{"x": 5,"y": 5}`
 
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "Not found!" }`


* **Sample Call:**

  ```curl
    curl 
		-X GET http://localhost:8080/mars/getsetup
  ```
  
<br />

`POST`  /mars/sonda
----
  Inserts a new sonda in Mars. Returns json data about the inserted sonda. If you insert a sonda with the id of a 
  existing sonda, the new sonda will overwrite the old one.

*  **Request Headers**
	<br /> Content-type: application/json <br />
	Accept: application/json
  
* **Data Params**

  `{"position": {"x": [integer],"y": [integer]},"heading": [string],"id":[integer]}`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{"position": {"x": 1,"y": 2},"heading": "N","id":1}`
 
* **Error Response:**

  * **Code:** 500 INTERNAL SERVER ERROR <br />
    **Content:** `{ error : "The surface's upper-right border wasn't set!" }`

  OR

  * **Code:** 500 INTERNAL SERVER ERROR <br />
    **Content:** `{ error : "The sonda cannot be created!" }`

  OR	
	
  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ error : "Not an Integer value!" }`
    
  OR

  * **Code:** 500 INTERNAL SERVER ERROR <br />
    **Content:** `{ error : "Not a valid heading!" }`    
       

* **Sample Call:**

  ```curl
    curl 
		-H "Accept: application/json" 
		-H "Content-type: application/json" 
		-X POST -d '{"position": {"x": 1,"y": 2},"heading": "N","id":1}' http://localhost:8080/mars/sonda
  ```
  
<br />

`PUT`  /mars/movesonda/:id
----
  Moves the sonda for the given id in Mars' surface. The movements are passed as a string and 
  can be either "L" for turning left, "R" for turning or "M" for moving.
  Returns json data about the sonda in its new Position.

*  **Request Headers**
	<br /> Content-type: application/json <br />
	Accept: application/json
 
*  **URL Params**

   **Required:**
 
   `id=[integer]`
   
* **Data Params**

  `{"movements": [string]}`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{"position": {"x": 1,"y": 3},"heading": "N","id":1}`
 
* **Error Response:**

  * **Code:** 500 INTERNAL SERVER ERROR <br />
    **Content:** `{ error : "The sonda with id = 1 doesn't exist!" }`


* **Sample Call:**

  ```curl
    curl 
		-H "Accept: application/json" 
		-H "Content-type: application/json" 
		-X PUT -d '{"movements": "LMLMLMLMM"}' http://localhost:8080/mars/movesonda/1
  ```  
  
<br />

`GET`  /mars/allsondas
----
  Returns json data about all the sondas in Mars

  
* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{
    "1": {
        "position": {
            "x": 1,
            "y": 2
        },
        "heading": "N",
        "id": 1
    },
    "2": {
        "position": {
            "x": 3,
            "y": 3
        },
        "heading": "E",
        "id": 2
    }
}`
 

* **Sample Call:**

  ```curl
    curl 
		-X GET http://localhost:8080/mars/allsondas
  ```
  
<br />

`GET`  /mars/getsonda/:id
----
  Returns json data about the sonda with the given id

*  **URL Params**

   **Required:**
 
   `id=[integer]`
   
* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{"position": {"x": 1,"y": 2},"heading": "N","id":1}`
 

* **Sample Call:**

  ```curl
    curl 
		-X GET http://localhost:8080/mars/getsonda/1
  ```  
  
<br />

`DELETE`  /mars/deletesonda/:id
----
  Removes the sonda for the given id from Mars

*  **Request Headers**
	<br /> Content-type: application/json <br />
	Accept: application/json
 
*  **URL Params**

   **Required:**
 
   `id=[integer]`
   

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `"Sonda with id = 1 was deleted"`
 
* **Error Response:**

  * **Code:** 500 INTERNAL SERVER ERROR <br />
    **Content:** `{ error : "The sonda with id 1 doesn't exist!" }`


* **Sample Call:**

  ```curl
    curl 
		-H "Accept: application/json" 
		-H "Content-type: application/json" 
		-X DELETE http://localhost:8080/mars/deletesonda/1
  ```    
