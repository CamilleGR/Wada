package Features;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.Node;
import static org.elasticsearch.node.NodeBuilder.*;

public class TestAPIJava {

    public static void main(String[] args) {

        Node nodeDefault = nodeBuilder().data(false).node();           // Création d'un noeud client (ne contient pas de données) par defaut (cluster elasticsearch)
        Node nodeDefault2 = nodeBuilder().client(true).node();         // Exactement la même chose

        Node localNode = nodeBuilder().local(true).node();           // Noeud local pour faire des tests

        Node node = nodeBuilder().clusterName("MonCluster").client(true).node(); //Création d'un noeud qui rejoint le cluster MonCluster

        Client client = node.client();                      //Création d'un objet Client sur le noeud "node"

        node.close();                                       //On ferme le noeud
    }
}
