# Ex1
Read Me Ex1:
Name: Liron Asaraf
ID:206955841
Course: OOP
About the project: in this project we use all the elements of "Graph Theory", and specific about undirected graph and weighted graph.
In this project we use the interfaces of node info, weighted graph and weighted graph algorithms.
The classes we made for them are:
WGraph_DS:
The class is implements the weighted graph and the class of node_info inside, that implements the interface of node info.
The functions that helps us build the class:
nodesMap= used  by HashTable.
Vertices= used by Hashset.
Edges= used by Map and HashMap.
getNode – in this function we get the key of the nodesMap.
hasEdge- in this function we check if we have the key of the node in the edges,
because if the key of the nodes are not inside the map of the edges, that is obviously that the node is not exist and also the node don't have edge so we can return.
getEdge- in this function we got the edge between two nodes, so we check if there is an edge between two node and return the edge.
addNode- we check if the node is not exist in the nodesMap, if he don't, we put the node in the nodesMap and vertices.
Connect- if we want to connect nodes we need first check if there is already edge between them, and check if the nodes are not even, then if they are not, we put edge between them.
Collection- we take all the Graph and put them in new HashSet (O1)
removeNode- we check if the node exist, if he doesn't, return.
If he does exist, delete him from the verices, delete his key from the nodesMap, and delete his edges with the others nodes.
Change the size of the graph.
removeEdge- we check if the nodes exists in the vertices map, and then we check if the edge between them exist, if it does we deleted the edge from both sides.
nodesSize- we get how much nodes we got in the graph.
edgeSize- we got how much edges we got in the graph.
getMC- we get how many changes we did in the graph by modeCount we put.
In this class we use inside class node_info and in this class we have:
Get key.
Get info- return the nodeData.
Set info.
Get tag.
Set tag.
WGraph_Algo- The class is implements the weighted graph algorithms:
The function that are in the class:
Copy- saves the same graph in another memory file.
initNodesTag- reset the tag of the nodes.
isConnected- first we check what is the size of the graph. If the graph is with 0 node or 1 node it is obviously connected.
We put all the vertices in a list and tag them with 1 (white), we pass all the nodes and all what we already pass we put in a stack (using dfs algorithms), than if there is 2 or more it means that all the graph is not connected, and if not it is connected.
shortestPathDist- I used the result of the shortestPath function and took the cost of the edges and it gives me the shortest path distance.
shortestPath- I used HashMap that put all the fathers (from which node "I came"), and make priority queue that put in the stack the nodes and compare them which nodes has the minimum edge weight and choose them. I made also hashSet with the visited node. Every current node I put in the queue and if it equal the goal I stop. 
For the save and loud I used Serializable and took the code from geeksforgeeks.
The tests were taken from the OOP course git and the graph were changed for testing.







