package com.example.easy_flow_backend.service.graph_services;

import com.example.easy_flow_backend.entity.GraphEdge;
import com.example.easy_flow_backend.entity.Line;
import com.example.easy_flow_backend.repos.GraphEdgeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GraphEdgeServiceImpl implements GraphEdgeService {
    @Autowired
    private GraphEdgeRepo graphEdgeRepo;

    public List<GraphEdge> getEdges(Line line) {
        List<GraphEdge> graphEdges = graphEdgeRepo.findAllByLine(line);
        return graphEdges;
    }

    @Override
    public boolean addEdges(List<GraphEdge> edges) {
        graphEdgeRepo.saveAll(edges);
        return true;
    }


}
