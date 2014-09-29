package playground.m.radziwon;

import java.io.FileWriter;

import org.jgrapht.Graph;
import org.jgrapht.ext.EdgeNameProvider;
import org.jgrapht.ext.GraphMLExporter;
import org.jgrapht.ext.VertexNameProvider;
import org.jgrapht.graph.AbstractGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.Pseudograph;

public class GraphmlExporter {
  public static void main(String[] args) throws Exception {
    AbstractGraph<String, DefaultEdge> graph = createSimpleGraph();
    saveToGraphML(graph, "mygraph.graphml");
    // after that:
    // 1.open the graph in yEd (3.11)
    // 2.Alt+Shift+H (hierarchical layout)
    // 3.Edit->Properties Mapper...->Configurations->New configuration for
    // Nodes->Add a new entry:
    // 3.1 Data source (vertex label), map to (label text), Conversion (auto)
  }


  private static AbstractGraph<String, DefaultEdge> createSimpleGraph() {
    Pseudograph<String, DefaultEdge> graph = new Pseudograph<String, DefaultEdge>(DefaultEdge.class);
    graph.addVertex("a");
    graph.addVertex("b");
    graph.addVertex("c");
    graph.addEdge("a", "b");
    graph.addEdge("b", "b");
    graph.addEdge("b", "c");
    return graph;
  }


  public static void saveToGraphML(final Graph<String, DefaultEdge> graph, String filename) throws Exception {
    VertexNameProvider<String> vertexIDProvider = new VertexNameProvider<String>() {
      @Override
      public String getVertexName(String vertex) {
        return vertex;
      }
    };
    VertexNameProvider<String> vertexNameProvider = new VertexNameProvider<String>() {
      @Override
      public String getVertexName(String vertex) {
        return vertex;
      }
    };
    EdgeNameProvider<DefaultEdge> edgeIDProvider = new EdgeNameProvider<DefaultEdge>() {
      @Override
      public String getEdgeName(DefaultEdge edge) {
        return graph.getEdgeSource(edge) + " > " + graph.getEdgeTarget(edge);
      }
    };
    EdgeNameProvider<DefaultEdge> edgeLabelProvider = new EdgeNameProvider<DefaultEdge>() {
      @Override
      public String getEdgeName(DefaultEdge edge) {
        return edge + "";
      }
    };
    GraphMLExporter<String, DefaultEdge> exporter = new GraphMLExporter<String, DefaultEdge>(vertexIDProvider, vertexNameProvider,
        edgeIDProvider, edgeLabelProvider);

    FileWriter fw = new FileWriter(filename);
    exporter.export(fw, graph);
  }
}
