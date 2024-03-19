@echo off
cd bin
javac EternalBreaker.java
java --module-path ../lib --add-modules javafx.controls,javafx.fxml EternalBreaker
pause