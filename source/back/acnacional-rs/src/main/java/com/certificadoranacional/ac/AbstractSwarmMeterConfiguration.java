package com.certificadoranacional.ac;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;

import com.google.common.base.CaseFormat;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;

public class AbstractSwarmMeterConfiguration {

  // @formatter:off
  private static final String [] SWARM_ENV_KEYS = {
      "SWARM_NODE_ID",
      "SWARM_NODE_NAME",
      "SWARM_SERVICE_ID",
      "SWARM_SERVICE_NAME",
      "SWARM_TASK_ID",
      "SWARM_TASK_NAME",
      "SWARM_TASK_KEY"
  };
  //@formatter:on

  private static final Map<String, String> SWARM_ENV;

  static {
    Map<String, String> map = new HashMap<>();
    for (String key : SWARM_ENV_KEYS) {
      map.put(key, System.getenv(key));
    }
    map.put("SWARM_TASK_KEY", AbstractSwarmMeterConfiguration.getTaskKey(map.get("SWARM_TASK_NAME")));
    SWARM_ENV = Collections.unmodifiableMap(map);
  }

  public AbstractSwarmMeterConfiguration() {
    super();
  }

  public MeterRegistryCustomizer<MeterRegistry> getMeterRegistryCustomizer(final String applicationName) {
    List<Tag> tags = new ArrayList<>();
    for (Entry<String, String> entry : AbstractSwarmMeterConfiguration.SWARM_ENV.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      if (!Strings.isNullOrEmpty(value)) {
        tags.add(new ImmutableTag(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key), value));
      }
    }
    tags.add(new ImmutableTag("application", applicationName));
    return registry -> registry.config().commonTags(tags);
  }

  private static String getTaskKey(String str) {
    if (str == null) {
      return null;
    }
    Splitter splitter = Splitter.on('.');
    List<String> list = splitter.splitToList(str);
    StringBuilder builder = new StringBuilder();
    builder.append(list.get(0));
    if (list.size() > 1) {
      builder.append("-");
      builder.append(list.get(1));
    }
    return builder.toString();
  }

}
