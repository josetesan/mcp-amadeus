package es.josetesan.ai.poc.mcp.amadeus.service;

import es.josetesan.ai.poc.mcp.amadeus.converter.ActivityConverter;
import es.josetesan.ai.poc.mcp.amadeus.converter.LocationConverter;
import es.josetesan.ai.poc.mcp.amadeus.model.Location;
import es.josetesan.ai.poc.mcp.amadeus.model.ShopActivity;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AIService {

    private final AmadeusService amadeusService;
    private final LocationConverter locationConverter;
    private final ActivityConverter activityConverter;

    public AIService(AmadeusService amadeusService, LocationConverter locationConverter, ActivityConverter activityConverter) {
        this.amadeusService = amadeusService;
        this.locationConverter = locationConverter;
        this.activityConverter = activityConverter;
    }

    @Tool(description = "Returns a list of recommendations of cities to travel")
    public List<Location> getRecommendedLocations(@ToolParam(description = "The IATA city code") String cityCode) throws Exception{
        return this.locationConverter.convert(this.amadeusService.getRecommendedLocations(cityCode, "ES")).getData();
    }

    @Tool(description = "Return a list of shopping activities or recommendations around a city, given its longitude and latitude data")
    public List<ShopActivity> getShoppingActivities(@ToolParam(description = "The longitude of the city") Double longitude, @ToolParam(description = "The latitude of the city") Double latitude) throws Exception {
        return Arrays.stream(this.amadeusService.getActivities(longitude,latitude))
                .map(activityConverter::convert)
                .toList();
    }
}
