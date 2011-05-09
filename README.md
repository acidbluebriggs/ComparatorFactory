##CompositeComparator##
-------------------------------
Allows two comparators to be merged together.

Allows two comparators to be merged, so that if the major comparator returns equal, the other comparator is used. This can then be used to form composite comparisons.

#Example:#

        java.util.Collections.sort(list, 
            new CompositeComparator( new BeanPropertyComparator("lastName"), 
                new BeanPropertycComparator("firstName") ) ); 

The example above sorts items on last name, and then first name. Two beans with the same last name will then be sorted on first name.

##SortUtil##
-------------------------------
A simple utility class for sorting collections based on its contained beans' properties.

      //example bean
        public class PersonBean {
 
            private String firstName;
            private String lastName;
 
            public PersonBean(String firstName, String lastName) {
                this.firstName = firstName;
                this.lastName  = lastName;
            }
 
            public final String getFirstName() {
                return this.firstName;
            }
 
            public final String getLastName() {
                return this.lastName;
            }
        }

        final List people = new LinkedList();
        people.add(new PersonBean("Benjamin", "Franklin"));
        people.add(new PersonBean("Ronald", "Reagan"));
        people.add(new PersonBean("George", "Bush"));
        people.add(new PersonBean("George", "Washington"));
 
        //sort by last name
        SortUtil.sort(people, "lastName");
 
        //sort by first name
        SortUtil.sort(people, "firstName");
 
        //sort by last name and first name
        SortUtil.sort(people, "lastname", "firstName");
