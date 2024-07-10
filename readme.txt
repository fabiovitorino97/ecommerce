POST http://localhost:8080/products
JSON: 	{
		"name": "Product A",
		"price": 10.00,
		"stock": 100,
		"active": true
	}

CRIA PRODUTO



GET http://localhost:8080/products
RETORNA TODOS OS PRODUTOS ATIVOS



GET http://localhost:8080/products/id
RETORNA O PRODUTO PELO ID INFORMADO



PUT http://localhost:8080/products/id
JSON: 	{
		"name": "Product A",
		"price": 15.00,
		"stock": 90,
		"active": false
	}
ATUALIZA O PRODUTO PELO ID



DELETE http://localhost:8080/products/id
DESATIVA A VENDA INFORMADO PELO ID



POST http://localhost:8080/sales
    JSON : 	{
                "items": [
                    {
                            "productId": 1,
                            "quantity": 2
                    },
                    {
                            "productId": 2,
                            "quantity": 3
                    }
                ]
        }
CRIA UMA VENDA COM O ID DO PRODUTO E A QUANTIDADE



POST http://localhost:8080/sales
RETORNA TODAS AS VENDAS CADASTRADAS



POST http://localhost:8080/sales/id
RETORNA UMA VENDA PELO ID INFORMADO



PUT http://localhost:8080/sales/id
JSON : 	{
    		"items": [
        		{
            			"productId": 1,
            			"quantity": 2
        		},
        		{
            			"productId": 2,
            			"quantity": 3
        		}
    		]
	}
ATUALIZA A VENDA PELO ID



DELETE http://localhost:8080/sales/id
DELETA A VENDA PELO ID INFORMADO



GET http://localhost:8080/sales/date?date={date} (EXEMPLO: http://localhost:8080/sales/date?date=2023-07-06)
FILTRA VENDAS PELA DATA INFORMADA



GET http://localhost:8080/sales/report/monthly?year=2023&month=7 (EXEMPLO: http://localhost:8080/sales/report/monthly?year=2024&month=7)
RELATORIO MENSAL DE VENDAS