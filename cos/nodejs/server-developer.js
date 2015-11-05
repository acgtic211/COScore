var $ = require('jQuery');

var http = require('http');
var process = require('process');

http.createServer(function (req, res) {
  res.writeHead(200, {'Content-Type': 'text/plain'});
  res.end('Hello World\n');
}).listen(1337, '127.0.0.1');

console.log('Server running at http://127.0.0.1:6969/');

var io = require('socket.io').listen(6969, {log: false});

console.log('io variable:' + io);

var soap = require('soap');

console.log('	soap variable:' + io);

// users which are currently available in chat
var users = {};
	
io.sockets.on('connection', function (socket){
  console.log('\n\n\n\n\n\n----------------------- Inside connection ----------------------------\n');

  socket.on('connectComponent', function(data){
  		socket.user = data;
		socket.join(data);
  });
  
  socket.on('login', function(data){
  	if(data != undefined){
  		var args_login = {userName: data.userName, userPassword: data.userPassword};

		console.log('DATA USERNAME -> ' + data.userName);
		console.log('DATA USERPASSWORD -> ' + data.userPassword);
		
		callWS('http://localhost:8080/cos/COSWS?wsdl', 'login', args_login, function(ws_response) {
		  
		  if(ws_response != undefined && ws_response != '-1') {
		  	// User to send
		  	console.log('UserID --> ' + ws_response);
		  	var user = ws_response;
		  	socket.user = ws_response;
			socket.join(ws_response);


			io.sockets.in(user).emit('userID', ws_response);

			// Avisamos en node.js de un login correcto
			console.log('[socket.on - login] Ok');

			// Tras inicio de sesión, se inicializa la interfaz
			var args_iniGUI = {userID: ws_response};
			callWS('http://localhost:8080/cos/COSWS?wsdl', 'initUserArchitecture', args_iniGUI, function(ws_response2) {
		  		if(ws_response2 != 'Error' && ws_response2 != undefined) {

					ws_response2.forEach(function(value, index){
						// Comprobar plataforma para devolver componentes
						if(value.platform == 'Web'){
							io.sockets.in(user).emit('addComponent', value.codeHTML);
						}else{
							if(value.platform == 'Java'){
								io.sockets.in(user).emit('addComponent', value.componentId, value.instanceId, 
									value.objectJava, value.jarJava);
							}
						}
					});
					io.sockets.in(user).emit('addComponent', '', '', '', '');
		  		
		  		} else {
		  			io.sockets.in(user).emit('addComponent', null);
			    	console.log('[socket.on - initUserArchitecture] Error to initUserArchitecture, possible model is impaired.');
		  		}	  
			});
		  } else {
				console.log('[socket.on - login] Error\n');
				io.sockets.in(user).emit('userID', ws_response);
		  }	  
		});
  	} else {
  		console.log("Error to login");
  	}	
  });

  // Socket for receiving initGUI calls
  socket.on('logout', function(data){
  	if(data != undefined){
  		var args_logout = {userID: data.userID};
	
		callWS('http://localhost:8080/cos/COSWS?wsdl', 'logout', args_logout, function(ws_response) {
		  console.log(ws_response);
		});
  	} else {
  		console.log("Error to logout");
  	}
  		
  });

  socket.on('deleteComponent', function(data) {
  	if(data != undefined){
  		console.log('[socket.on - send] user: ' + data.userID);
		console.log('[socket.on - send] componentId: ' + data.componentId);
	  	var args_deleteComponent = {userID: data.userID, componentId: data.componentId};
		
		callWS('http://localhost:8080/cos/COSWS?wsdl', 'deleteComponent', args_deleteComponent, function(ws_response) {
			console.log('[socket.on - send] deleteComponent: ' + ws_response);
			io.sockets.in(data.userID).emit('deleteComponent', ws_response);		
		});
  	} else {
  		console.log("Error to delete component");
  	}	
  });

  socket.on('addComponent', function(data) {
  	if(data != undefined){
  		console.log('[socket.on - send] user: ' + data.userID);
		console.log('[socket.on - send] componentId: ' + data.componentId);
	  	var args_addComponent = {userID: data.userID, componentId: data.componentId};
		
		callWS('http://localhost:8080/cos/COSWS?wsdl', 'addComponent', args_addComponent, function(ws_response) {
		 	console.log('addComponent');

		  	if(ws_response != 'Error' && ws_response != undefined) {

				ws_response.forEach(function(value, index){
					// Comprobar plataforma para devolver componentes
					if(value.platform == 'Web'){
						io.sockets.in(data.userID).emit('addComponent', value.codeHTML);
					}else{
						if(value.platform == 'Java'){
							io.sockets.in(data.userID).emit('addComponent', value.componentId, value.instanceId, 
								value.objectJava, value.jarJava);
						}
					}
				});
				io.sockets.in(data.userID).emit('addComponent', '', '', '', '');
				
			} else {
				io.sockets.in(data.userID).emit('addComponent', null);
				console.log('[socket.on - initUserArchitecture] Error to initUserArchitecture, possible model is impaired.');
			}
		});
  	} else {
  		console.log("Error to add component");
  	}	
  });

  socket.on('loadInteraction', function(data) {
  	if(data != undefined){
  		console.log('[socket.on - send] user: ' + data.userID);
		console.log('[socket.on - send] idInstance: ' + data.idInstance);
		console.log('[socket.on - send] action: ' + data.action);
		console.log('[socket.on - send] property: ' + data.property);
		console.log('[socket.on - send] value: ' + data.value);
  		var args_loadInteraction = {userID: data.userID, idInstance: data.idInstance, action: data.action, property: data.property, value: data.value};
	
		callWS('http://localhost:8080/cos/COSWS?wsdl', 'loadInteraction', args_loadInteraction, function(ws_response) {
		});
  	} else {
  		console.log("Error to load interaction");
  	}
  });
  

  //############### COMUNICATION BETWEEN COMPONENTS ##########################
  /*socket.on('send', function(user, component, c_id, port, data, d_schema){
	console.log('[socket.on - send] user: ' + user);
	console.log('[socket.on - send] component: ' + component);
	console.log('[socket.on - send] c_id: ' + c_id);
	console.log('[socket.on - send] port: ' + port);
	console.log('[socket.on - send] data: ' + data);
	console.log('[socket.on - send] d_schema: ' + d_schema);
	console.log('\n\n');

	var args_calculateConnectedPorts = {userID: user, componentID: component, idInstance: c_id, portID: port};
	callWS('http://localhost:8080/cos/COSWS?wsdl', 'calculateConnectedPorts', args_calculateConnectedPorts, function(ws_response) {
	  //console.log('WS Response: ' + ws_response);

	  var pairs = undefined;

	  if(ws_response != undefined)
	  	pairs = ws_response.split('-');

	  if(pairs == undefined){
	  		console.log('[socket.on - receiving] undefined');
	  }else{
	  		//console.log('Pairs: ' + pairs);

			pairs.forEach(function(pair){
	    		pair_s = pair.split(',');
				var c = pair_s[0];
				var i = pair_s[1];
				var p = pair_s[2];
	    		console.log('Component: ' + c);
	    		console.log('Instance: ' + i);
	    		console.log('Port: ' + p);
		
				console.log('Send: ' + c + ',' + i + ',' + p);
				console.log('\n\n');
				io.sockets.in(user).emit(c + ',' + i + ',' + p, data);
	  		});
	  }
	  	  
	});	
  });*/  

  socket.on('send', function(_user, component, c_id, port, data, d_schema){
	console.log('[socket.on - send] user: ' + _user);
	console.log('[socket.on - send] component: ' + component);
	console.log('[socket.on - send] c_id: ' + c_id);
	console.log('[socket.on - send] port: ' + port);
	console.log('[socket.on - send] data: ' + data);
	console.log('[socket.on - send] d_schema: ' + d_schema);
	console.log('\n\n');

	var elements =  _user.split(',');
	var user = elements[0];

	if(elements[1] == undefined){
		// Need past to CORE to emit
		// This params are to emit the infomation
		var args_calculateConnectedPorts = {userID: user, componentID: component, idInstance: c_id, portID: port};
		callWS('http://localhost:8080/cos/COSWS?wsdl', 'calculateConnectedPorts', args_calculateConnectedPorts, function(ws_response) {

			  var pairs = undefined;

			  if(ws_response != undefined)
			  	pairs = ws_response.split('-');

			  if(pairs == undefined){
			  		console.log('[socket.on - receiving] undefined');
			  }else{
			  		//console.log('Pairs: ' + pairs);

					pairs.forEach(function(pair){
			    		pair_s = pair.split(',');
						var c = pair_s[0];
						var i = pair_s[1];
						var p = pair_s[2];
			    		console.log('Component: ' + c);
			    		console.log('Instance: ' + i);
			    		console.log('Port: ' + p);
				
						console.log('Send: ' + c + ',' + i + ',' + p);
						console.log('\n\n');
						io.sockets.in(user).emit(c + ',' + i + ',' + p, data);
			  		});
			  }
		  	  
		});
	}else{
		// Don't need past to CORE to emit
		// This params are to receive information
		io.sockets.in(user).emit(component + ',' + c_id + ',' + port, data);

		var args_loadInteraction = {userID: user, idInstance: (component + ',' + c_id), action: "receive_information", property: "port", value: port};
	
		callWS('http://localhost:8080/cos/COSWS?wsdl', 'loadInteraction', args_loadInteraction, function(ws_response) {
		});
	}	
  });

});

// Method defined to call Web Services and to get the return values
function callWS(wsurl, methodname, args, callback) {

  //console.log('WS URL: ' + wsurl);
  soap.createClient(wsurl, function(err, client, ret2) {
	
	if(client==null) {
	  console.log('[callWS] Error');
	  callback('Error');
	}
	else {	
		client[methodname](args, function(err, result) {
		  //console.log(result);
		  //console.log(result.return);
		  
		  callback(result.return);		  
		});
	}	
  });
}
