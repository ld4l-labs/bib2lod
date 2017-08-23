# Bib2Lod Change Log

* This change log records API changes that may affect implementing projects.

## Commit # -

### Added

* Added BuildParams.setGrandparent() and BuildParams.getGrandparent(), in 
order to pass in the parent's parent to the builder.


### Changed

* Instantiate LegacySourceDataEntityBuilder on program startup rather than
every time it's used.

* Changed BuildParams.setParentEntity() to BuildParams.setParent(), 
BuildParams.getParentEntity() to BuildParams.getParent().



## Commit #27b5857f

### Changed

* XmlTextElement.setTextValue() changed to XmlTextElement.retrieveTextValue():
more accurate name since the method does not set anything.

* Entity relationships, attributes, external relationships now stored as 
maps of unique lists - i.e., there are no duplicate elements in the list for 
a given property.

* Entity types stored as unique list - i.e., there are no duplicate types in
the list.

* Type-to-builder map and associated methods in EntityBuilderFactory, 
EntityBuilder, and implementations now use specific Types rather than
Type classes as keys; e.g., key Ld4lActivityType.class is replaced by
Ld4lActivityType.PUBLISHER_ACTIVITY. This allows each enum value to define
its own builder, rather than one builder per enum.