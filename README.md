# StarTRackerForAndroid
New and improved star tacker

This is java code for star-tracking algorithm to determine the orientation of autonomous platforms such as nano-satellites, drones, vessels and automobiles, using commercial-off-the-shelf mobile devices such as smartphones. 

The novelty of the proposed framework lies in both the computational efficiency and the ability of the star-tracker algorithm to cope with noisy measurements and outliers using affordable COTS mobile platforms. The presented algorithm was implemented and tested on several Android mobile devices. The expected accuracy of the reported orientation is 0.1 degree, 0.5 \degree.

The suggested new algorithm can report robust orientation even in suboptimal conditions such as light pollution, partially cloudy sky and outliers (such as airplanes). The rapid hashing process makes the algorithm suited for embedded low-cost devices. 
Based on field experiments, we conclude that modern smartphones are capable of detecting stars and computing the expected orientation even in suboptimal scenarios. Combined with $MEMS$ gyro, the star-tracker orientation can be reported in high rates (over 100Hz). 
The use of such accurate orientation sensors may benefit a wide range of mobile platforms, including autonomous cars, $UAV$ drones and nano-satellites. The presented algorithm is currently being implemented on a pico-satellite which is scheduled to be launched in 2019.


For more information regardeing this algorithm : https://ieeexplore.ieee.org/abstract/document/7806068 


