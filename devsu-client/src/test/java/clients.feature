Feature: Testing Clients REST API

Scenario: Testing valid GET endpoint
  Given url 'http://localhost:8095/clientes/1'
  When method GET
  Then status 200
