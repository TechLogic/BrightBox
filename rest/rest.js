var express = require('express');
var mysql = require('mysql');
var mysqltorest  = require('mysql-to-rest');
var app = express();

var connection = mysql.createConnection({
  host     : 'localhost',
  user     : 'root',
  password : 'root',
  database : 'bright_box_db'
});

connection.connect();
var api = mysqltorest(app,connection);

app.listen(8000);

