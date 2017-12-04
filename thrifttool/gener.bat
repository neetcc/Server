@echo off

FOR %%e IN (*.thrift) DO (
  echo gen java %%e
  call thrift.exe --gen java %%e
 )
 pause