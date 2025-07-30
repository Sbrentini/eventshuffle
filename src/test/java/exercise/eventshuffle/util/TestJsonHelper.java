package exercise.eventshuffle.util;

public class TestJsonHelper {
    public static final String EVENT_JSON =
    """
    {
      "id": 1,
      "name": "Jake's secret party",
      "dates": [
        "2014-01-01",
        "2014-01-05",
        "2014-01-12"
      ],
      "votes": [
        {
          "date": "2014-01-01",
          "people": [
            "John",
            "Julia",
            "Paul",
            "Daisy"
          ]
        }
      ]
    }
    """;
}
