# CarLoading
## Project target
This project target is to optimize process of loading car with given packages, fitting to the constraints.<br>
Some of the packages can be placed on another, and (or not) another on them. Also, there is requirement that packages from previous sections need placed before farther sections.
Access to each section cannot be disturbed in straight line by any of preceding sections.<br>
We can specify two types of data: *instance* and *solution*. Also, let's assume we have *section* -
 each starts with the number of rows, and following after data with some scheme. Each column is separated with **tabulation**, each row ended with **line separator**.
 
## Input and output scheme
Instance input has 3 sections:
- vehicle, which has 1 row,
- hosts, // we can say, it is area took on the floor
- actual packages.

> booleans are `1` as `true`, or `0` as `false`.
 
So it should looks like bellow:
````
1 <vehicle section - we have just one vehicle>
<vehicle width> <vehicle height>
<number of hosts>
<host id> <host length> <host width>
...
<number of packages>
<pack id> <pack's sequence id> <host id> <pack hegith> <can be placed on another package> <can another pack be placed on>
...
````


Solution contains two sections.<br>
First is built from car's length needed to load received placement of parcels, and their occupied place on the floor.<br>
Second contains packages placement.

Solution output scheme is:
```
1 <just one row, because one result>
<necessary car's length> <took by packages place on the floor>
<length of placement's list>
<package id> <is package reversed by 90 deg> <dist. from the left vehicle edge> <dist. from the vehicle front>
```

For example, for input (instance)
```
1
35 26
3
epal 8 12
eur2 12 10
eur6 8 6
4
p1 3 epal 10 1 1
p2 3 epal 10 1 1
p3 1 eur2 12 0 1
p4 2 eur6 8 1 1
```

we can receive (solution):
````
1
12      264
4
p3      0       0       0
p4      0       10      0
p2      0       16      0
p1      0       16      0
````

## How to run
> First of all, JDK 8 is required (tested on JDK 8 update 131). <br>

You can use included `gradle wraper` (version 3.5-rc-2) or installed on your computer (tested on gradle 4.0.1).<br>
Invokable commands: <br>
| Command    | Task |
| ---------- | ------------------ |
| `gradle validate -Pinstance="<instance file path>" -Psolution="<solution file path>"` | *validates given instance and solution with constraints* |
| `gradle construct` | *creates just one solution for the given in the next step instance* |
| `gradle optimize` | *as same as above, but generates only better than preceding solutions infinity* |

*In `construct` and `optimize` you need to pass values through the console.*
> Constraints values are placed in `constraints.properties`,<br>
 as same as solver properties in `solver.properties` in `resources` directory.<br>
 All values are integers.