Overview of the solution:

How to run->
run java/Application.java file's main method. It computes the following-
1. It reads an input csv file from resources.
2. Then groups all the sentences using the algorithm which generates generic keys.
4. Then it writes the output into output.csv file in the required format.

Algorithm ->> 
     
     * We assume each word in a sentence to be a repeating word and mask it with '?'. This will generate all possible combinations of keys/groups.
     
     * This new sentence is cloned and used as a key, so next time same key is formed we add those notes to the corresponding key(masked)
     
     * We also know which word is being masked so we store it in a cloned Entry object, as this object can be saved in multiple groups


Code complexity->
*The time complexity is O(n^2)+O(n^2). First to group all the sentences and second is to generate output format.
*So overall time complexity is O(n^2)
Space complexity-> O(n*m) as we are storing groups and their sets

*From Scalability perspective if the size of file increases, 
then we might see some memory issues, which can be tuned according to the usage limits.

*If we had to do the same thing in 2 weeks, I would make use of some database like elasticsearch, 
this can be utilised for large amount of dataset and we can query data for any pattern matching(also grouping) requirements based on the inverted indexes.
This will solve the memory issue above.



