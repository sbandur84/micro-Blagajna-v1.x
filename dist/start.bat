@echo off
REM    uniCenta oPOS Touch Friendly Point of Sales designed for Touch Screen
REM    Copyright (c) 2009-20143 uniCenta
REM    http://sourceforge.net/projects/unicentaopos
REM
REM    This file is part of uniCenta oPOS
REM
REM    uniCenta oPOS is free software: you can redistribute it and/or modify
REM    it under the terms of the GNU General Public License as published by
REM    the Free Software Foundation, either version 3 of the License, or
REM    (at your option) any later version.
REM
REM    uniCenta oPOS is distributed in the hope that it will be useful,
REM    but WITHOUT ANY WARRANTY; without even the implied warranty of
REM    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
REM    GNU General Public License for more details.
REM
REM    You should have received a copy of the GNU General Public License
REM    along with uniCenta oPOS.  If not, see http://www.gnu.org/licenses/>
REM
set DIRNAME=%~dp0
set CP="%DIRNAME%mikroBLAGAJNA.jar"
set CP=%CP%;"%DIRNAME%lib/jasperreports-4.5.1.jar"
set CP=%CP%;"%DIRNAME%lib/jcommon-1.0.15.jar"
set CP=%CP%;"%DIRNAME%lib/jfreechart-1.0.12.jar"
set CP=%CP%;"%DIRNAME%lib/jdt-compiler-3.1.1.jar"
set CP=%CP%;"%DIRNAME%lib/commons-beanutils-1.8.3.jar"
set CP=%CP%;"%DIRNAME%lib/commons-digester-2.1.jar"
set CP=%CP%;"%DIRNAME%lib/iText-2.1.7.jar"
set CP=%CP%;"%DIRNAME%lib/poi-3.8-20120326.jar"
set CP=%CP%;"%DIRNAME%lib/barcode4j-2.0.jar"
set CP=%CP%;"%DIRNAME%lib/commons-codec-1.4.jar"
set CP=%CP%;"%DIRNAME%lib/velocity-1.7-dep.jar"
set CP=%CP%;"%DIRNAME%lib/oro-2.0.8.jar"
set CP=%CP%;"%DIRNAME%lib/commons-collections-3.2.1.jar"
set CP=%CP%;"%DIRNAME%lib/commons-lang-2.4.jar"
set CP=%CP%;"%DIRNAME%lib/bsh-core-2.0b4.jar"
set CP=%CP%;"%DIRNAME%lib/RXTXcomm.jar"
set CP=%CP%;"%DIRNAME%lib/jpos1121.jar"
set CP=%CP%;"%DIRNAME%lib/swingx-all-1.6.4.jar"
set CP=%CP%;"%DIRNAME%lib/substance.jar"
set CP=%CP%;"%DIRNAME%lib/substance-swingx.jar"

REM Apache Axis SOAP libraries.
set CP=%CP%;"%DIRNAME%lib/axis.jar"
set CP=%CP%;"%DIRNAME%lib/jaxrpc.jar"
set CP=%CP%;"%DIRNAME%lib/saaj.jar"
set CP=%CP%;"%DIRNAME%lib/wsdl4j-1.5.1.jar"
set CP=%CP%;"%DIRNAME%lib/commons-discovery-0.4.jar"
set CP=%CP%;"%DIRNAME%lib/commons-logging-1.1.jar"
set CP=%CP%;"%DIRNAME%locales/"
set CP=%CP%;"%DIRNAME%reports/"

start /B javaw -cp %CP% -Djava.library.path="%DIRNAME%lib/Windows/i368-mingw32" -Ddirname.path="%DIRNAME%./" -zacetni_zaslon.png com.openbravo.pos.forms.StartPOS %1
