after changes on the proto files and before running always do:
	- ensure proto files consistent in both modules
	- maven clean compile for the backend
	- gradle clean build for the app

when DB changes increment VERSION variable

!!! if nothing working do invalidate caches restart !!!