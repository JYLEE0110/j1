package org.zerock.j1.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// entity를 dto / dto를 entity로 변환
@Configuration
public class RootConfig {
    
     @Bean
    public ModelMapper getMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                // LOOSE => ReplyEntity안에 ManyTo one 인Board를 매칭 시켜준다.
                .setMatchingStrategy(MatchingStrategies.LOOSE);

        return modelMapper;
    }
}
