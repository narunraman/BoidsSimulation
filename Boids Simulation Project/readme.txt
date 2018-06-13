Partners:
Narun Raman and Nathaniel Sauerberg

Final Project Description:
Our idea is to create a simulation of Boids flock algorithm. Boids is an artificial life program dictated, at its most basic level, by three simple rules: separation, alignment, and cohesion. Separation is an incentive for the "bird-oid object" or  boids to avoid crowding local flockmates. Alignment is an incentive for the boids to steer towards the average heading of local flockmates. Lastly, cohesion is an incentive for the boids to move toward the average position of the local flockmates. 
Adding on top of the basic rules, we are also adding obstacles that will repel the boids. These could be singular point repulsions or walls. Additionally, the walls around the simulation boundary could be simply obstacles as defined previously, or "circular" edges where if a boid goes out one side it appears on the opposite side. Lastly, we will add functionality to toggle any of these rules on or off.
Here are some other feature we might add if we have time and the complexity of the previous feature are not very high. Different species, this could be implemented through coloration of the boids and boids rules would only apply to boids within the same species. Change map size which would effectively zoom out and have the boundaries a subsection of the simulation space. Lastly, a save simulation state feature that takes a snapshot of the objects' location and velocity at a given time and a continuous visualization of the simulation over the entire simulation runtime. 

MVC implementation:
Model:
The model is the central component of the simulation, directly managing the data and logic of the application. This includes the three rules for Boids simulation and the objects that are a part of the simulation. These objects include the boids and the boudary objects, and include their own unique data such as velocity or location.

View:
A visual representation of the boids seen through moving objects displayed in the viewport. We believe that the user would rather the simulation as a realtime graphical process, rather than a dynamically updating table of data. The layout of the view will be the simulation space in a box in the viewport and a panel containing the toggles for the basic rules and other features regarding the boids or the obstacles. 

Core Classes in the Model:
Boid, Obstacle



