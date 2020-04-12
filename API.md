# Webapp12 - API REST Documentation
--------

## About our API
Our API REST is about advertisements, users, blogs and comments in them. If you want to use it just
follow these rules, either way you will probably recieve error messages.

## API requests

## How to use our API
1. Download [Postman](https://www.getpostman.com/).
2. You only can send GET, POST, PUT and DELETE requests.

## API requests

### Resources
The resource API has GET, POST, PUT and DELETE methods.
http: // localhost: 8443 followed by the containt request URL.

**All API queries have been preceded by /api**


## Blog

#### Get one page blog
get 5 blogs

* ##### URL:

	< /blogs?page=0&number=5 >

* ##### Method:

	`GET`
	
* ##### Success Response:
  
```
  [
    {
        "id": 22,
        "title": "CALIDEZ Y CARÁCTER SE ENCUENTRAN FRENTE A FRENTE EN ESTA CASA",
        "text": "Los propietarios de esta casa unifamiliar en una población cercana a Barcelona —una pareja con hijos adolescentes— querían reformarla para adaptarla a los tiempos modernos. Solo querían un lavado de cara y que les ayudáramos a escoger mobiliario y textiles para modernizar la vivienda.",
        "images": []
    },
    {
        "id": 23,
        "title": "TENDENCIAS EN COCINAS QUE TE VOLVERÁN CRAZY ESTE 2020",
        "text": "VERDE NATURAL La preocupación por la sostenibilidad ha hecho que los tonos verdes más naturales se cuelen en nuestras cocinas durante este 2020. ACENTOS TURQUESA Los tonos turquesa no solo aportan luminosidad a la cocina, sino también mucha frescura. Además, resaltan un montón con otros colores de base como el blanco, y siempre se ven muy limpios. Ideales para armarios y azulejos.",
        "images": []
    },
    {
        "id": 24,
        "title": "Piscinas en tu propia casa",
        "text": "Acercate y prueba",
        "images": []
    },
    {
        "id": 25,
        "title": "¿Madrid es caro?",
        "text": "No si alquilas con estas inmobiliarias",
        "images": []
    },
    {
        "id": 26,
        "title": "Trucos para conseguir el mejor alquiler",
        "text": "Aprende ya",
        "images": []
    }
  ]
```
#### Get one  blog
get one blog

* ##### URL:

	< /blogs/id >

* ##### Method:

	`GET`
	
* ##### Success Response:
  
```
    {
        "id": 22,
        "title": "CALIDEZ Y CARÁCTER SE ENCUENTRAN FRENTE A FRENTE EN ESTA CASA",
        "text": "Los propietarios de esta casa unifamiliar en una población cercana a Barcelona —una pareja con hijos adolescentes— querían reformarla para adaptarla a los tiempos modernos. Solo querían un lavado de cara y que les ayudáramos a escoger mobiliario y textiles para modernizar la vivienda.",
        "images": []
    }
```

	

#### Edit  blog
Edit a blog

* ##### URL:

	< /blogs/id >

* ##### Method:

	`PUT`
  
* ##### Data Params

```
{

    "title": "Editando el blog",
    "text": "Los propietarios de esta casa unifamiliar en una población cercana a Barcelona —una pareja con hijos adolescentes— querían reformarla para adaptarla a los tiempos modernos. Solo querían un lavado de cara y que les ayudáramos a escoger mobiliario y textiles para modernizar la vivienda.",
    "images": []
}
```
* ##### Success Response:
  
```
  {
      "id": 22,
      "title": "Editando el blog",
      "text": "Los propietarios de esta casa unifamiliar en una población cercana a Barcelona —una pareja con hijos adolescentes— querían reformarla para adaptarla a los tiempos modernos. Solo querían un lavado de cara y que les ayudáramos a escoger mobiliario y textiles para modernizar la vivienda.",
      "images": []
  }
```
* #### Error Response:
```
  {
      "timestamp": "2020-03-10T08:32:03.395+0000",
      "status": 403,
      "error": "Forbidden",
      "message": "Forbidden",
      "path": "/api/blogs/22"
   }
```
#### Delete blog
Delete blog

* ##### URL:

	< /blogs/id >

* ##### Method:

	`DELETE`

* ##### Success Response:
  
```
  {
      "id": 22,
      "title": "Nuevo blog",
      "text": "Los propietarios de esta casa unifamiliar en una población cercana a Barcelona —una pareja con hijos adolescentes— querían reformarla para adaptarla a los tiempos modernos. Solo querían un lavado de cara y que les ayudáramos a escoger mobiliario y textiles para modernizar la vivienda.",
      "images": []
  }
```
* #### Error Response:
```
  {
    "timestamp": "2020-03-10T08:37:35.663+0000",
    "status": 403,
    "error": "Forbidden",
    "message": "Forbidden",
    "path": "/api/blogs/22"
  }
```
#### Create blog
Create blog

* ##### URL:

	< /blogs >

* ##### Method:

	`POST`
* ##### Data Params

```
{

    "title": "Nuevo blog",
    "text": "Los propietarios de esta casa unifamiliar en una población cercana a Barcelona —una pareja con hijos adolescentes— querían reformarla para adaptarla a los tiempos modernos. Solo querían un lavado de cara y que les ayudáramos a escoger mobiliario y textiles para modernizar la vivienda.",
    "images": []
}
```
* ##### Success Response:
  
```
  {
      "id": 31,
      "title": "Nuevo blog",
      "text": "Los propietarios de esta casa unifamiliar en una población cercana a Barcelona —una pareja con hijos adolescentes— querían reformarla para adaptarla a los tiempos modernos. Solo querían un lavado de cara y que les ayudáramos a escoger mobiliario y textiles para modernizar la vivienda.",
      "images": []
  }
```
* #### Error Response:
```
  {
    "timestamp": "2020-03-10T08:37:35.663+0000",
    "status": 403,
    "error": "Forbidden",
    "message": "Forbidden",
    "path": "/api/blogs"
  }
```

## Main
#### Get Image


* ##### URL:

	< /main/images/{fileName:.+} >

* ##### Method:

	`GET`
	
* ##### Example of query:
	api/main/images/bg_2.jpg
	

* ##### Success Response:

![Image](../uptown/images/bg_2.jpg)

#### Upload Image

* ##### URL:

	< main/images >

* ##### Method:

	`POST`
	
* ##### URL Params:
	empty
	
* ##### Data Params:	
	"id": "9"
	
	file[]: image file format (*.jpg, *.jpeg, *.png)	

* ##### Success Response:

```
{
    "advertisement": {
        "id": 9,
        "type": "Alquiler",
        "property": "Piso",
        "rooms": 3,
        "bathrooms": 1,
        "squareMeters": 90,
        "location": "Pontevedra",
        "address": "calle carlos v,4,2 C",
        "price": 1600.0,
        "picture": "4991e20c-9169-4c2b-a4f6-7e03957cda1c_AD70010.jpg",
        "images": [
            "work-3.jpg"
        ],
        "comments": []
    },
    "mensaje": "Has subido correctamente la imagen: 4991e20c-9169-4c2b-a4f6-7e03957cda1c_AD70010.jpg"
}
```

## Advertisement
#### Get Advertisement


* ##### URL:

	< /advertisements/ >

* ##### Method:

	`GET`

* ##### Data Params:

	page=[int]
	number=[int]

* ##### Example of query:	

	/api/advertisements/?page=0&number=1
	
* ##### Success Response:
```
[
    {
        "id": 31,
        "type": "Venta",
        "property": "Piso",
        "rooms": 1,
        "bathrooms": 1,
        "squareMeters": 233,
        "location": "Madrid",
        "address": "C/Duero nº1",
        "price": 233.0,
        "picture": null,
        "images": [
            "AD01712.jpg"
        ],
        "comments": []
    }
]
```

#### Get Advertisement


* ##### URL:

	< /advertisements/ >

* ##### Method:

	`POST`

* ##### Data Params:

	id=[long]
	type=[String]
	property=[String]
	rooms=[int]
	bathrooms=[int]
	squareMeters=[int]
	location=[String]
	adrress=[String]
	price=[double]

	
* ##### Success Response:
```
[
    {
        "id": 1,
        "type": "Venta",
        "property": "Local",
        "rooms": 2,
        "bathrooms": 2,
        "squareMeters": 200,
        "location": "Madrid",
        "address": "Madrid",
        "price": 200.0,
        "picture": null,
        "images": [],
        "comments": null
    }
]
```


## Authorithation

#### Login


* ##### URL:

	< /login >

* ##### Method:

	`GET`
	
* ##### Success Response:
```
{
    "id": 17,
    "name": "Angel",
    "email": "angel@gmail.com",
    "password": "$2a$10$JJbC1FpqBreLseg3QEh0QeDYu.p1t.q/miJncAU77uTNgRbbwBRtK",
    "roles": [
        "ROLE_USER"
    ],
    "myFavourites": [
        {
            "id": 7,
            "type": "Venta",
            "property": "Casa",
            "rooms": 4,
            "bathrooms": 2,
            "squareMeters": 120,
            "location": "Madrid",
            "address": "calle azul,2",
            "price": 200000.0,
            "picture": null,
            "images": [
                "work-1.jpg"
            ],
            "comments": [
                {
                    "id": 6,
                    "author": {
                        "id": 5,
                        "name": "Maria",
                        "email": "maria@gmail.com",
                        "password": "$2a$10$0fNV/XpQ2xh0cu8D.VnrHu4OF9L1.8Ir6PylJtHbdw4I/rmkP2bhe",
                        "roles": [
                            "ROLE_ADMIN"
                        ],
                        "myFavourites": [],
                        "mySearches": [],
                        "myAdvertisements": []
                    },
                    "message": "Hola, me ha encantado"
                }
            ]
        },
        {
            "id": 8,
            "type": "Venta",
            "property": "Local",
            "rooms": 2,
            "bathrooms": 1,
            "squareMeters": 50,
            "location": "Madrid",
            "address": "calle verde,3",
            "price": 120000.0,
            "picture": null,
            "images": [
                "work-7.jpg"
            ],
            "comments": []
        },
        {
            "id": 9,
            "type": "Alquiler",
            "property": "Piso",
            "rooms": 3,
            "bathrooms": 1,
            "squareMeters": 90,
            "location": "Pontevedra",
            "address": "calle carlos v,4,2 C",
            "price": 1600.0,
            "picture": null,
            "images": [
                "work-3.jpg"
            ],
            "comments": []
        }
    ],
    "mySearches": [
        {
            "id": 18,
            "type": "Alquiler",
            "property": null,
            "rooms": 2,
            "bathrooms": 1,
            "squareMeters": 60,
            "location": "Madrid",
            "address": null,
            "price": 800.0
        },
        {
            "id": 19,
            "type": "Alquiler",
            "property": null,
            "rooms": 3,
            "bathrooms": 2,
            "squareMeters": 80,
            "location": "Mostoles",
            "address": null,
            "price": 1000.0
        },
        {
            "id": 20,
            "type": "Alquiler",
            "property": null,
            "rooms": 4,
            "bathrooms": 3,
            "squareMeters": 100,
            "location": "Mostoles",
            "address": null,
            "price": 1200.0
        },
        {
            "id": 21,
            "type": "Alquiler",
            "property": null,
            "rooms": 2,
            "bathrooms": 2,
            "squareMeters": 70,
            "location": "Madrid",
            "address": null,
            "price": 900.0
        }
    ],
    "myAdvertisements": [
        {
            "id": 10,
            "type": "Venta",
            "property": "Casa",
            "rooms": 2,
            "bathrooms": 1,
            "squareMeters": 56,
            "location": "Madrid",
            "address": "calle verde,3",
            "price": 78990.0,
            "picture": null,
            "images": [
                "work-5.jpg"
            ],
            "comments": []
        },
        {
            "id": 12,
            "type": "Venta",
            "property": "Local",
            "rooms": 4,
            "bathrooms": 2,
            "squareMeters": 85,
            "location": "Mostoles",
            "address": "calle verde,3",
            "price": 140000.0,
            "picture": null,
            "images": [
                "work-7.jpg"
            ],
            "comments": []
        },
        {
            "id": 13,
            "type": "Alquiler",
            "property": "Local",
            "rooms": 3,
            "bathrooms": 1,
            "squareMeters": 78,
            "location": "Mostoles",
            "address": "calle verde,3",
            "price": 1200.0,
            "picture": null,
            "images": [
                "work-6.jpg"
            ],
            "comments": []
        }
    ]
}
```

#### Logout


* ##### URL:

	< /logout >

* ##### Method:

	`GET`
	
* ##### Success Response:
```true```

#### Register


* ##### URL:

	< /register >

* ##### Method:

	`POST`
	
* ##### Data Params

```
{
    "name": "David",
    "email": "david@gmail.com",
    "password": "$2a$10$JJbC1FpqBreLseg3QEh0QeDYu.p1t.q/miJncAU77uTNgRbbwBRtK",
    "roles": [
        "ROLE_USER"
    ],
    "myFavourites": [],
    "comments": [],
    "mySearches": [],
    "myAdvertisements": []
}
```
* ##### Success Response:
```
{
    "id": 31,
    "name": "David",
    "email": "david@gmail.com",
    "password": "$2a$10$jOzrveyw3nvnvIR3VR1OsOzmlAitSvtHITXiCZ99l72oJZshxvisW",
    "roles": [
        "ROLE_USER"
    ],
    "myFavourites": [],
    "mySearches": [],
    "myAdvertisements": []
}
```


#### Add advertisement


* ##### URL:

	< /users/{id} >

* ##### Method:

	`PUT`
	

* ##### Success Response:
```
{
    "id": 1,
    "name": "Edu",
    "email": "edu@gmail.com",
    "password": "$2a$10$jLSau3KtJpkfVuswocQL0.IiuIzWNicXG.bP3JWay9XIapi853rdy",
    "roles": [
        "ROLE_USER"
    ],
    "myFavourites": [
        {
            "id": 31,
            "type": "Venta",
            "property": "Piso",
            "rooms": 1,
            "bathrooms": 1,
            "squareMeters": 233,
            "location": "Madrid",
            "address": "C/Duero nº1",
            "price": 233.0,
            "picture": null,
            "images": [
                "AD01712.jpg"
            ],
            "comments": []
        }
    ],
    "mySearches": [],
    "myAdvertisements": [
        {
            "id": 31,
            "type": "Venta",
            "property": "Piso",
            "rooms": 1,
            "bathrooms": 1,
            "squareMeters": 233,
            "location": "Madrid",
            "address": "C/Duero nº1",
            "price": 233.0,
            "picture": null,
            "images": [
                "AD01712.jpg"
            ],
            "comments": []
        }
    ]
}
```
* #### Error Response:
```
  {
    "timestamp": "2020-03-10T08:37:35.663+0000",
    "status": 403,
    "error": "Forbidden",
    "message": "Forbidden",
    "path": "/api/users/31"
  }
```

#### Delete Favourites


* ##### URL:

	< /users/favourites/{id} >

* ##### Method:

	`DELETE`
	
* ##### Success Response:
```
{
    "id": 1,
    "name": "Edu",
    "email": "edu@gmail.com",
    "password": "$2a$10$jLSau3KtJpkfVuswocQL0.IiuIzWNicXG.bP3JWay9XIapi853rdy",
    "roles": [
        "ROLE_USER"
    ],
    "myFavourites": [],
    "mySearches": [],
    "myAdvertisements": [
        {
            "id": 31,
            "type": "Venta",
            "property": "Piso",
            "rooms": 1,
            "bathrooms": 1,
            "squareMeters": 233,
            "location": "Madrid",
            "address": "C/Duero nº1",
            "price": 233.0,
            "picture": null,
            "images": [
                "AD01712.jpg"
            ],
            "comments": []
        }
    ]
}
```
* #### Error Response:
```
  {
    "timestamp": "2020-03-10T08:37:35.663+0000",
    "status": 403,
    "error": "Forbidden",
    "message": "Forbidden",
    "path": "/api/users/favourites/31"
  }
```
#### Delete Advertisements


* ##### URL:

	< /users/advertisements/{id} >

* ##### Method:

	`DELETE`

* ##### Success Response:
```
{
    "id": 1,
    "name": "Edu",
    "email": "edu@gmail.com",
    "password": "$2a$10$jLSau3KtJpkfVuswocQL0.IiuIzWNicXG.bP3JWay9XIapi853rdy",
    "roles": [
        "ROLE_USER"
    ],
    "myFavourites": [
        {
            "id": 31,
            "type": "Venta",
            "property": "Piso",
            "rooms": 1,
            "bathrooms": 1,
            "squareMeters": 233,
            "location": "Madrid",
            "address": "C/Duero nº1",
            "price": 233.0,
            "picture": null,
            "images": [
                "AD01712.jpg"
            ],
            "comments": []
        }
    ],
    "mySearches": [],
    "myAdvertisements": []
}
```
* #### Error Response:
```
  {
    "timestamp": "2020-03-10T08:37:35.663+0000",
    "status": 403,
    "error": "Forbidden",
    "message": "Forbidden",
    "path": "/api/users/advertisements/31"
  }
```
#### Get Favourites


* ##### URL:

	< /users/favourites?page=0&number=1 >

* ##### Method:

	`GET`

* ##### Success Response:
```
[
    {
        "id": 31,
        "type": "Venta",
        "property": "Piso",
        "rooms": 1,
        "bathrooms": 1,
        "squareMeters": 233,
        "location": "Madrid",
        "address": "C/Duero nº1",
        "price": 233.0,
        "picture": null,
        "images": [
            "AD01712.jpg"
        ],
        "comments": []
    }
]
```
* #### Error Response:
```
  {
    "timestamp": "2020-03-10T08:37:35.663+0000",
    "status": 403,
    "error": "Forbidden",
    "message": "Forbidden",
    "path": "/api/users/favourites?page=0&number=1"
  }
```
## Comments

#### Add a comment

* ##### URL:

	< /advertisements/{id}/comments >

* ##### Method:

	`POST`
* ##### Data Params

```
    {
        "message": "hola caracola"
     }
```
* ##### Success Response:
  
```
{
    "id": 31,
    "author": "Angel",
    "message": "hola caracola"
}
```
* #### Error Response:
```
  {
    "timestamp": "2020-03-10T08:37:35.663+0000",
    "status": 403,
    "error": "Forbidden",
    "message": "Forbidden",
    "path": "/api/advertisements/7/comments"
  }
```
#### Get comments

* ##### URL:

	< /advertisements/{id}/comments?page=0&number=2 >

* ##### Method:

	`GET`

* ##### Success Response:
  
```
[
    {
        "id": 6,
        "author": "Maria",
        "message": "Hola, me ha encantado"
    },
    {
        "id": 31,
        "author": "Angel",
        "message": "hola caracola"
    }
]
```

#### Add a comment

* ##### URL:

	< advertisement/{id}/comments/{idComment} >

* ##### Method:

	`DELETE`


* ##### Success Response:
  
```
{
    "id": 6,
    "author": "Maria",
    "message": "Hola, me ha encantado"
}
```
* #### Error Response:
```
  {
    "timestamp": "2020-03-10T08:37:35.663+0000",
    "status": 403,
    "error": "Forbidden",
    "message": "Forbidden",
    "path": "/api/advertisements/7/comments/6"
  }
```








	

