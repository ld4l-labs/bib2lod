# Bib2Lod Change Log

This change log records API changes that may affect implementing projects.

## Commit #9d16ad80

### Changed

* Removed 'xml' from package names:
  * org.ld4l.bib2lod.conversion.xml.marcxml
  * org.ld4l.bib2lod.entitybuilders.xml.marcxml 
  * org.ld4l.bib2lod.entitybuilders.xml.marcxml.activities

New package names:

  * org.ld4l.bib2lod.conversion.marcxml
  * org.ld4l.bib2lod.entitybuilders.marcxml
  * org.ld4l.bib2lod.entitybuilders.marcxml.activities
  
Implementers can do the same with their packages.


## Commit #26cef60d (and preceding)

### Added
* Added BuildParams.setGrandparent() and BuildParams.getGrandparent(), in 
order to pass in the parent's parent to the builder.

### Changed

* Instantiate LegacySourceDataEntityBuilder on program startup rather than
every time it is used.

* Changed BuildParams.setParentEntity() to BuildParams.setParent(), 
BuildParams.getParentEntity() to BuildParams.getParent(). 

* Changed BuildParams.subfield to BuildParams.subfields (a list of subfields
rather than a single subfield). Builders previously expecting a single
subfield will use only the first subfield in the list. Previous calls to 
BuildParams.setSubfield() and BuildParams.getSubfield() will behave as 
previously. Defined methods:
  * BuildParams.setSubfield(RecordField subfield) - resets the list and adds
subfield - thus replaces previous BuildParams.setSubfield().
  * BuildParams.setSubfields(List<RecordField> subfields) - resets the list
and adds all subfields.
  * BuildParams.addSubfield(RecordField subfield) - adds subfield to the
list without resetting it.
  * BuildParams.addSubfields(List<RecordField> subfields - adds all 
subfields to the list without resetting it.
  * BuildParams.getSubfield(int index) - returns the subfield at the index. 
Returns null if there is no subfield at the index.
  * BuildParams.getSubfield() - returns the subfield at index 0 - thus 
behaves like the previous BuildParams.getSubfield(). Returns null if the 
list is empty.
  * BuildParams.getSubfields() - returns all subfields.


## Commit #27b5857f (and preceding)

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