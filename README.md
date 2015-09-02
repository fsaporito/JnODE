# JnODE
Java Numerical Ordinary Differential Equation Solver



We are interested in studying numerical methods for solving first order initial value Cauchy problems.

By the Cauchy-Lipschitz theorem, we know that there exist a unique function y(t) that solves the problem in a neighbourhood of y0 if the function f is Lipschitz continuous in y and continuous in t. 
The existence of y(t) doesn't imply that it can be written in analytical form (i.e. a combination of simple functions), so we must resort to approximating y(t) via numerical methods in the general case.
Also, we study only first order problems since every differential equations of order n can be written as a system of n differential equations of order one.

The numerical methods work by calculating the approximated function y(t) on a discrete interval [t0,..,tn]. 
The difference among the various methods lies in how the approximation of y(tk) is computed.

Every method is characterised by the following properties:

 - Order: The order of a method is the grade of the error committed in the approximation.
 - Convergence: A method is convergent if it approximate the exact solution.
 - Stability: A method is stable if small input  variations lead to small output  variations. 

Higher order methods are required for obtaining a better precision without sacrificing the performances (i.e. by incrementing the number of steps required).

There are two main classes of numerical methods for solving differential equations, the Range-Kutta methods (RK) and the Linear Multistep methods (LM).
The RK methods work by dividing the [tk,tk+1] interval in more subintervals, to obtain a better approximation of every step of the approximated solution.
The LM methods instead work by considering  yk+1 not only dependant from yk (single step), but also from the previously calculated yi.

There is a more general class of numerical methods that encompasses the RK and the LM methods, the General Linear Methods class (GLM), which uses both a subdivision of the interval and the history of the approximated solution.

Adding a further distinction, numerical methods can be explicit or implicit. Explicit methods calculate the future state yk+1  only considering the present state yk, while implicit ones consider even the future state yk+1.
Explicit methods are less computationally expensive, but are never unconditionally stable (problems which can't be solved by explicit method because of this instability are called stiff problems). 
Implicit methods instead, while requiring more calculations, can be unconditionally stable, hence they can potentially be used for solving stiff problems (but the unconditional stability must be proved for the particular problem).
A preventive analysis should be done when possible to ascertain the stiffness of the problem (which cannot be always determined beforehand), to chose the kind of method to employ.



We will implement all these numerical methods in a Java library and study their properties, such as stability, convergence and error handling. In particular, we will analyse the performance-precision ratio for the various methods, using a set of prominent examples of first order differential equations from various fields (i.e. physics, engineering and biology), considering both stiff and nonstiff problems.

Software-wise, the implementation will be focused both on performance and code design, with particular care on documentation and code generality. If time permits, parallel computation will be added for the main methods to improve performance.

Regarding the interaction with the user, a simple GUI will be provided to input the differential equations (which will be treated as a particular object internally). Other input methods will be evaluated and potentially implemented, such as message passing or interaction with files/databases.



Bibliography (Tentative, will be expanded along the way)

Coddington, Ordinary Differential Equations
Giusti, Analisi Matematica 2
Griffiths, Higham, Numerical Methods for Ordinary Differential Equations
Hairer, Noersett, Wanner, Solving Ordinary Differential Equations Volume 1 Nonstiff Problems
Jackiewicz, General Linear Methods For Ordinary Differential Equations
Quarteroni, Sacco, Saleri, Matematica Numerica



Methods:
EulerExplicit - Explicit, Order 1 (RangeKutta, LinearMultistep) - Implemented
EulerImplicit - Implicit, Order 1 (RangeKutta, LinearMultistep)- Implemented
Heun - Explicit, Order 2 (RangeKutta) - Implemented
Ralston - Explicit, Order 2 (RangeKutta) - Implemented
MidPointExplicit - Explicit, Order 2 (RangeKutta) - Implemented
MidPointImplicit - Implicit, Order 2 (RangeKutta)
Kutta3 - Explicit, Order 3 (RangeKutta) - Implemented
RK4 - Explicit, Order 4 (RangeKutta)- Implemented
GaussLegendre4 - Implicit, Order 4 (RangeKutta)
GaussLegendre6 - Implicit, Order 6 (RangeKutta)
Radau3 - Implicit, Order 3 (RangeKutta)
Radau5 - Implicit, Order 5 (RangeKutta)
