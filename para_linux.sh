#!/bin/bash


limpiar(){
cd

cd Equipo-01

cd sensorHW
mvn clean eclipse:clean

cd ..

cd dispositivoHW

mvn clean eclipse:clean

cd ..

cd sistemaGestionEnergetica

mvn clean eclipse:clean

cd ..
}


buildear(){
cd

cd Equipo-01

cd sensorHW
mvn eclipse:eclipse

cd ..

cd dispositivoHW

mvn eclipse:eclipse

cd ..

cd sistemaGestionEnergetica

mvn eclipse:eclipse

cd ..
}

case $1 in
	clean)
		limpiar
		;;
	build)
		buildear
		;;
esac
