Liquibase Utils
-----------------
[![Build Status](https://travis-ci.com/evantill/liquibase-utils.svg?branch=master)](https://travis-ci.com/evantill/liquibase-utils)

Liquibase Utils is an extension to Liquibase to add preconditions.

## Installation

* Liquibase Requirements: **Requires Liquibase 3.8.0+**
* Download the [`liquibase-utils.jar`](https://github.com/evantill/liquibase-utils/releases/latest) file from:
   - Direct: ["release" section on github](https://github.com/evantill/liquibase-utils/releases/latest)
   - Maven: Group `com.github.evantill`, Artifact `liquibase-utils`, Version `0.0.1`

## Quick start

In order to use this extension, you must have the [`liquibase-utils.jar`](https://github.com/evantill/liquibase-utils/releases/latest)
jar in your classpath.

For XML change logs, define the <code>utils</code> namespace as below:

```XML
<?xml version="1.0" encoding="UTF-8"?>
<databaseChangeLog
   xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xmlns:utils="http://www.liquibase.org/xml/ns/dbchangelog-ext/liquibase-utils"
   xsi:schemaLocation= "http://www.liquibase.org/xml/ns/dbchangelog
                        http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.8.xsd 
                        http://www.liquibase.org/xml/ns/dbchangelog-ext/liquibase-utils 
                        http://evantill.github.com/liquibase-utils/liquibase-utils.xsd">
</databaseChangeLog>
```

Using the XML elements defined by this extension requires specifying the <code>utils</code>
namespace prefix on those elements.  Here is a quick example of how to require a [context](https://docs.liquibase.com/concepts/advanced/contexts.html):

```XML
   <preConditions>
      <utils:contextDefined/>
   </preConditions>
```

Refer to the [documentation](http://evantill.github.com/liquibase-utils) for further information.
