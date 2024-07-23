PRIMEIRO PASSO:

POST http://localhost:8080/api/permissions

JSON:   {
          "description": "ADMIN"
        }

JSON2:  {
          "description": "MANAGER"
        }

JSON3:  {
          "description": "COMMON_USER"
        }

GET  http://localhost:8080/api/permissions

Lista todas as pemissões possiveis



SEGUNDO PASSO:

POST http://localhost:8080/api/users/register

JSON: {
         "username": "admin",
         "fullname": "Nome do admin",
         "password": "12345678",
         "email": "um email valido",
         "permissions": [
            {
                "id": 1
            }
         ]
      }

JSON2: {
         "username": "commonuser",
         "fullname": "Nome do commonuser",
         "password": "12345678",
         "email": "um email valido",
         "permissions": [
            {
                "id": 3
            }
         ]
        }

POST http://localhost:8080/api/users/login

JSON   {
         "username": "admin",
         "password": "12345678"
       }

JSON2  {
         "username": "commonuser",
         "password": "12345678"
       }



TERCEIRO PASSO:

POST http://localhost:8080/api/products

JSON: 	{
		"name": "Product 1",
		"price": 10.00,
		"stock": 100,
		"active": true
        }

JSON: 	{
		"name": "Product 2",
		"price": 10.00,
		"stock": 100,
		"active": true
        }

JSON: 	{
		"name": "Product 3",
		"price": 10.00,
		"stock": 100,
		"active": true
        }

JSON: 	{
		"name": "Product 4",
		"price": 10.00,
		"stock": 100,
		"active": true
        }

JSON: 	{
		"name": "Product 5",
		"price": 10.00,
		"stock": 100,
		"active": true
        }

GET http://localhost:8080/api/products
RETORNA TODOS OS PRODUTOS ATIVOS

GET http://localhost:8080/api/products/id
RETORNA O PRODUTO PELO ID INFORMADO

PUT http://localhost:8080/api/products/id
JSON: 	{
		"name": "Product A",
		"price": 15.00,
		"stock": 90,
		"active": true
	    }

DELETE http://localhost:8080/api/products/id
DESATIVA A VENDA INFORMADO PELO ID



QUARTO PASSO:

POST http://localhost:8080/api/sales

JSON: 	{
                "items": [
                    {
                            "productId": 2,
                            "quantity": 5
                    },
                    {
                            "productId": 3,
                            "quantity": 5
                    }
                ]
        }

JSON2: 	{
                "items": [
                    {
                            "productId": 3,
                            "quantity": 5
                    },
                    {
                            "productId": 4,
                            "quantity": 5
                    }
                ]
        }

JSON3: 	{
                "items": [
                    {
                            "productId": 5,
                            "quantity": 5
                    },
                    {
                            "productId": 2,
                            "quantity": 5
                    }
                ]
        }

JSON4: 	{
                "items": [
                    {
                            "productId": 3,
                            "quantity": 5
                    },
                    {
                            "productId": 4,
                            "quantity": 5
                    }
                ]
        }

JSON5: 	{
                "items": [
                    {
                            "productId": 5,
                            "quantity": 5
                    },
                    {
                            "productId": 2,
                            "quantity": 5
                    }
                ]
        }

GET http://localhost:8080/api/sales
RETORNA TODAS AS VENDAS CADASTRADAS

GET http://localhost:8080/api/sales/id
RETORNA UMA VENDA PELO ID INFORMADO

PUT http://localhost:8080/sales/id
JSON : 	{
    		"items": [
        		{
            			"productId": 2,
            			"quantity": 10
        		},
        		{
            			"productId": 3,
            			"quantity": 10
        		}
    		]
	}

DELETE http://localhost:8080/api/sales/id
DELETA A VENDA PELO ID INFORMADO

GET http://localhost:8080/api/sales/date?date={date} (EXEMPLO: http://localhost:8080/api/sales/date?date=2024-07-23)
FILTRA VENDAS PELA DATA INFORMADA

GET http://localhost:8080/api/sales/report/monthly?year=2024&month=7 (EXEMPLO: http://localhost:8080/sales/report/monthly?year=2024&month=7)
RELATORIO MENSAL DE VENDAS



QUINTO PASSO:

POST http://localhost:8080/api/reset-password/request?username=admin

POST http://localhost:8080/api/reset-password/reset?token=5826053b-6661-4eab-8c06-f497af5052ae&newPassword=NovaSenha123

5826053b-6661-4eab-8c06-f497af5052ae