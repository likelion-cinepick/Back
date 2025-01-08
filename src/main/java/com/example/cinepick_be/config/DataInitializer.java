package com.example.cinepick_be.config;

import com.example.cinepick_be.entity.Question;
import com.example.cinepick_be.repository.QuestionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer {

   @Autowired
   private QuestionRepository questionRepository;

   @Bean
   public CommandLineRunner init(QuestionRepository questionRepository) {
      return args -> {
         if (questionRepository.count() == 0) {
            questionRepository.save(new Question("\uD83C\uDFE0\uD83C\uDF89\u2028새로 이사온 옆집.\u2028매일매일 파티를 여는데 초대장을 받은 나...", Arrays.asList("초대해줘서 고맙다며 파티에 즐겁게 참석한다.", "마음은 고마우나 벌써 피곤하여 거절한다.")));
            questionRepository.save(new Question("\uD83C\uDF0F\uD83E\uDDF5\u2028세상을 구한 히어로와 친구가 된 나.\u2028자신의 슈트를 선물로 주겠다는데,", Arrays.asList("중고나라에 슈트를 팔 생각을 한다.", "슈트를 입고 세상을 구하는 상상을 한다.")));
            questionRepository.save(new Question("\uD83E\uDE84\uD83D\uDD6F\uFE0F\u2028마법 학교에 입학하게 된 나.\u2028그러다 같은 반 친구의 교칙 위반을 발견하는데,", Arrays.asList("우선 친구니까 친구에게 먼저 이유를 물어본다.", "잘못된 행동이니까 바로 교장에게 말한다.")));
            questionRepository.save(new Question("\uD83D\uDEF3\uFE0F\uD83D\uDC40\u2028배를 타고 여행 중 이상형을 발견.\u2028사랑에 빠진 내가 할 행동은? ", Arrays.asList("일단 직전! 바로 가서 인스타를 물어본다.", "자연스럽게 접근하기 위해 계획부터 짠다. ")));
            questionRepository.save(new Question("\uD83C\uDF82\uD83E\uDDC1\u2028친구의 생일파티에 초대받은 나.\u2028생일 선물로 준비해간 것은?", Arrays.asList("눈에 띄는 크고 화려한 생일 케이크", "친구만을 위해 준비한 작고 귀여운 컵 케이크")));
            questionRepository.save(new Question("\uD83D\uDDFC\uD83D\uDCDC\u2028파리에 여행을 간 나.\u2028자정이 되면 과거로 여행할 수 있다는 소문을 듣는데,", Arrays.asList("말도 안되는 소문이니 한 귀로 듣고 흘린다.", "과거 파리에서 유명했던 사람들에 대해 생각한다.")));
            questionRepository.save(new Question("\uD83D\uDC09\uD83D\uDD25\u2028나와 더 친해질 수 있는 친구는?", Arrays.asList("힘든 상황에서 따뜻한 위로를 건내는 <센과 치히로의 행방불명> 하쿠", "객관적으로 상황을 파악하고 조언을 해주는 <엘리멘탈> 엠버")));
            questionRepository.save(new Question("\uD83D\uDD70\uFE0F\uD83D\uDCA1\u2028성인이 될 때 아빠가 알려준 비밀,\u2028시간 여행을 할 수 있다는 사실을 알게 된 나.", Arrays.asList("일단 당장 과거로 가본다.", "과거에서 하고 싶은 일을 정리한다.")));
            questionRepository.save(new Question("\uD83D\uDC51\uD83D\uDC4A\u2028유명한 칠공주 멤버가 된 나.\u2028다른 파와의 싸움을 준비하는데...", Arrays.asList("당당하게 앞에 서서 기선 제압한다.", "뒤에 서서 최대한 몰래 친구들을 보조한다.")));
            questionRepository.save(new Question("\uD83D\uDC67\uD83C\uDF92\u2028친하게 지내던 이웃집 여자 아이.\u2028어느 순간부터 잘 보이지 않는다.", Arrays.asList("학교 생활이 바쁜가보다 생각한다.", "안 좋은 일이 생긴 것은 아닌지 걱정부터 한다.")));
            questionRepository.save(new Question("\uD83E\uDDBE\uD83D\uDD76\uFE0F\u2028갑자기 나타나 도움을 요청하는 인물.\u2028미래에서 나를 구하러 온 로봇이라 밝히는데...", Arrays.asList("당황스럽지만 가능한 선에서 도움을 준다. ", "낯선 이를 믿을 수 없으니 신고부터 한다.")));
            questionRepository.save(new Question("☄\uFE0F\uD83C\uDF2A\uFE0F\u2028마야 달력의 재앙 예측.\u2028지구 종말까지 하루 남았다.", Arrays.asList("행동파! 당장 하고 싶은 일들을 한다.", "하루를 어떻게 알차게 보낼지 계획한다.")));
         }
      };
   }
}