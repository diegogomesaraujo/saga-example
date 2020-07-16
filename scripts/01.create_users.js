
use stock;

db.createUser({
	user: 'stock-app',
	pwd: 'stock@123',
	roles: [
		{ role: 'readWrite', db: 'stock' }
	]
});

use payment;

db.createUser({
	user: 'payment-app',
	pwd: 'payment@123',
	roles: [
		{ role: 'readWrite', db: 'payment' }
	]
});

use order;

db.createUser({
	user: 'order-app',
	pwd: 'order@123',
	roles: [
		{ role: 'readWrite', db: 'order' }
	]
});