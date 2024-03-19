@echo off
cd bin
javac --module-path ../lib --add-modules javafx.controls,javafx.fxml EternalBreaker.java
java --module-path ../lib --add-modules javafx.controls,javafx.fxml EternalBreaker
pause