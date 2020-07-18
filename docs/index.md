Liquibase Utils
-----------------
[![Build Status](https://travis-ci.org/evantill/liquibase-utils.svg?branch=master)](https://travis-ci.org/evantill/liquibase-utils)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.evantill/liquibase-utils)](https://search.maven.org/search?q=g:com.github.evantill%20a:liquibase-utils)

Liquibase Utils is an extension to [Liquibase] to add common utilities like [preconditions][liquibase-doc-preconditions]

### Preconditions

#### `contextDefined`

```XML
   <preConditions>
      <utils:contextDefined/>
   </preConditions>
```

#### `contextMatch`

```XML
   <preConditions>
      <utils:contextMatch expression="(a and b) or !c"/>
   </preConditions>
```

## Installation

* Liquibase Requirements: **Requires Liquibase 3.8.0+**
* Download the [`liquibase-utils.jar`][latest-release] file from:
   - Direct: ["release" section on github][latest-release]
   - Maven: 
       - Group ID `com.github.evantill`
       - Artifact ID `liquibase-utils`
       - Version `0.2.0`

## Quick start

In order to use this extension, you must have the [`liquibase-utils.jar`][latest-release]
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
                        https://evantill.github.io/liquibase-utils/liquibase-utils-0.2.xsd">
</databaseChangeLog>
```

Using the XML elements defined by this extension requires specifying the <code>utils</code>
namespace prefix on those elements.  Here is a quick example of how to require a [context][liquibase-doc-contexts]:

```XML
   <preConditions>
      <utils:contextDefined/>
   </preConditions>
```

Refer to the [documentation] for further information.

[latest-release]: https://search.maven.org/search?q=a:liquibase-utils
[documentation]: https://evantill.github.io/liquibase-utils/

[liquibase]: https://www.liquibase.org/ 
[liquibase-doc-preconditions]: https://docs.liquibase.com/concepts/advanced/preconditions.html?Highlight=preconditions 
[liquibase-doc-contexts]: https://docs.liquibase.com/concepts/advanced/contexts.html
