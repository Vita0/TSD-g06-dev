#-------------------------------------------------------------------------------
# Copyright (c) 2014-2016 University of Luxembourg.
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
# 
# Contributors:
#     Alfredo Capozucca - initial API and implementation
#	  Anton Nikonienkov - adapted for iCrash H5 Vaadin implementation
#-------------------------------------------------------------------------------

iCrash Java Distributed Web Development 
===================================

What Is This?
-------------
This Java project contains the implementation of the iCrash case study
specified according to the Messir methodology and using the Excalibur tool.




Prerequisites
--------------------------
1. Setup the Development environment setup as indicated in this webpage:

http://messir.uni.lu:8090/confluence/display/EXCALIBUR/iCrash+Distributed+Web+Application



How To Install The Project
--------------------------
1. Import the lu.uni.lassy.excalibur.examples.icrash.dev.web.java.server Java Web Development project into your workspace. 

2. Build the project (if option Project -> Build Automatically has not been selected) 

3. Right-click on the lu.uni.lassy.excalibur.examples.icrash.dev.web.java.server project and then select Properties

4. Select "Project Facets", then modify Configuration preset - instead of <Custom> choose "Vaadin 7",
then go to Runtimes tab, uncheck all Apache Tomcat entries and click OK

5. Right-click on the lu.uni.lassy.excalibur.examples.icrash.dev.web.java.server project and then select Ivy -> Resolve

6. On Eclipse IDE toolbar locate an icon with tooltip "Compile Vaadin Widgetset", and click on it



How To Execute The Project?
----------------------------------
Launching the webapp
Right-click on the iCrash project and then select Run as -> Run on Server



How To Check The MySQL Engine? (optional)
------------------------------------------
Checking whether the MySQL is up and ready
The package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.testcases
contains a simple test case meant to test whether the MySQL is installed and is ready to receive queries.

To execute this test case you should:

1. Open the class TestCase_db_alive contained in the the package 
lu.uni.lassy.excalibur.examples.icrash.dev.web.java.testcases

2. Right-click on the class and then select Run as -> Java Application



Available web application URL's
----------------------------------
http://localhost:8080/iCrash/creator - Messir Creator console
http://localhost:8080/iCrash/activator - Messir Activator console
http://localhost:8080/iCrash/comcompanies#!00 (where 00 is Communication Company number) - Messir Communication Company console
http://localhost:8080/iCrash/admin - Messir Administrator console
http://localhost:8080/iCrash/coordinators#!01 (where 01 is Coordinator's ID) - Messir Coordinator console (currently optimized for mobile only)



Admin credentials?
--------------------------
There exists a built-in user called "admin". Its credentials are:
user name: icrashadmin
password: 7WXC1359
 
This user allows to create the other system users who are known as "Coordinators".