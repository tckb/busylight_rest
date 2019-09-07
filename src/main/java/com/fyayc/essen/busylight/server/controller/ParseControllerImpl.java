package com.fyayc.essen.busylight.server.controller;

import com.fyayc.essen.busylight.server.controller.intf.LightController;
import com.fyayc.essen.busylight.server.controller.intf.ParseController;
import com.fyayc.essen.busylight.server.controller.intf.StatusController;
import com.fyayc.essen.busylight.server.controller.intf.ToneController;
import com.fyayc.essen.busylight.server.controller.models.Response;
import com.google.common.collect.ImmutableMap;
import java.awt.Color;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParseControllerImpl implements ParseController {
  private final StatusController statusController;
  private final LightController lightController;
  private final ToneController toneControllerImpl;
  private final Pattern pattern =
      Pattern.compile("(set my)(\\s)+(status|light|tone)(\\s)+(to|for|as|ass)(\\s)+(.*)");
  private final Map<String, Color> colorHashMap;
  private Logger logger = LogManager.getLogger(this.getClass());

  @Autowired
  public ParseControllerImpl(
      StatusController statusController,
      LightController lightController,
      ToneController toneController) {
    this.statusController = statusController;
    this.lightController = lightController;
    this.toneControllerImpl = toneController;
    colorHashMap =
        ImmutableMap.<String, Color>builder()
            .put("red", Color.RED)
            .put("green", Color.GREEN)
            .put("blue", Color.BLUE)
            .put("magenta", Color.MAGENTA)
            .put("orange", Color.ORANGE)
            .put("pink", Color.pink)
            .put("yellow", Color.yellow)
            .put("cyan", Color.cyan)
            .build();
  }

  @Override
  public Response parse(String text) {
    logger.info("Parsing request {}", text);
    try {
      Matcher matcher = pattern.matcher(text);
      Response response = new Response("Sorry, this is not available yet");
      if (matcher.matches()) {
        switch (matcher.group(3)) {
          case "status":
            response = statusController.status(matcher.group(7));
            break;
          case "light":
            Color color = colorHashMap.get(matcher.group(7));
            if (color != null) {
              response =
                  lightController.light(
                      "0x" + Integer.toHexString(color.getRGB()).substring(2), "none");
            } else {
              response = lightController.light(matcher.group(7), "none");
            }
            break;
          case "tone":
            break;
        }
        return response;
      } else {
        return new Response("Sorry, I'm unable to understand this. Can you try again?");
      }

    } catch (IllegalArgumentException exception) {
      return new Response(exception.getMessage());
    }
  }
}
