apcs-project
============

Repository for AP CS Final Project - Team Pluto (Peter Duchovni &amp; Lydia Goldberg)

The proposed final project is a logical language database construction and access tool.
The tentative language syntax is as follows:


DELIMITERS
==========

'.' ends a definition (de-dents completely)
',' continues a list (indentation remains unchanged)
';' ends a sub-list (up one indent level)
':' preceeds list (down one indent level)


DECLARATIONS
============


clarify: creates a new property that represents a special case of another property
as: preceeds name of sub-property


let: defines an object type and its properties
have: preceeds list of properties



PROPERTY ATTRIBUTES
===================

Properties are named variables with definite type. Properties are all lowercase, while types are upper-case.

The following properties are globally defined:

int: an unsigned integer
string: a string of ascii characters
bool: a integer boolean (true/false) value, with 0 as false and 1 as true.
group: construct enclosing multiple properties

univ: universal variables are references to particular properties of a type.
          Movies and books have titles, so a query for titles of movies and books would use universals
          
      universals are all-caps
          
bind: binds universal property to local property
to: gives name of variable whose value is to be taken as the unviversal value

of: sub-quality of group


          clarify int as year.
          clarify string as name.
          clarify string as title.
          
          clarify group as person:
            name Name,
            year BirthYear,
            bool Alive,
            year DeathYear.
          
          univ name CREATOR.
          univ year CREATED.
          univ title TITLE.
          
          let Book have:
            person Author,
            title Title,
            bind TITLE to Title,
            bind CREATOR to Name of Author,
            year Written,
            bind CREATED to Written.
          
          let Movie have:
            person Director,
            title Title,
            bind TITLE to Title,
            bind CREATOR to Name of Director,
            year Released,
            bind CREATED to Released.

