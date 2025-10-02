# State Diagram

```plantuml
@startuml
state new
state waiting
state searching
state following
state finished
new --> waiting : start
waiting --> searching : search
waiting --> finished : found
searching --> following : follow
following --> waiting : searchagain
@enduml
``