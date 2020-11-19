package app.coronawarn.server.services.distribution.statistics.keyfigurecard.factory;

import app.coronawarn.server.common.protocols.internal.stats.KeyFigure;
import app.coronawarn.server.common.protocols.internal.stats.KeyFigure.Rank;
import app.coronawarn.server.common.protocols.internal.stats.KeyFigure.Trend;
import app.coronawarn.server.common.protocols.internal.stats.KeyFigure.TrendSemantic;
import app.coronawarn.server.common.protocols.internal.stats.KeyFigureCard;
import app.coronawarn.server.common.protocols.internal.stats.KeyFigureCard.Builder;
import app.coronawarn.server.services.distribution.statistics.StatisticsJsonStringObject;
import app.coronawarn.server.services.distribution.statistics.keyfigurecard.KeyFigureCardSequenceConstants;
import app.coronawarn.server.services.distribution.statistics.keyfigurecard.ValueTrendCalculator;
import java.util.List;

public class KeySubmissionCardFactory extends HeaderCardFactory {

  public KeySubmissionCardFactory(ValueTrendCalculator valueTrendCalculator) {
    super(valueTrendCalculator);
  }

  @Override
  protected Integer getCardId() {
    return KeyFigureCardSequenceConstants.KEY_SUBMISSION_CARD_ID;
  }

  private KeyFigure getPersonWhoSharedKeysDaily(StatisticsJsonStringObject stats) {
    return KeyFigure.newBuilder()
        .setValue(stats.getPersonsWhoSharedKeysDaily())
        .setRank(Rank.PRIMARY)
        .setDecimals(0)
        .setTrend(Trend.UNSPECIFIED_TREND)
        .setTrendSemantic(TrendSemantic.UNSPECIFIED_TREND_SEMANTIC)
        .build();
  }

  private KeyFigure getPersonWhoSharedKeysCumulated(StatisticsJsonStringObject stats) {
    return KeyFigure.newBuilder()
        .setValue(stats.getPersonsWhoSharedKeysCumulated())
        .setRank(Rank.TERTIARY)
        .setDecimals(0)
        .setTrend(Trend.UNSPECIFIED_TREND)
        .setTrendSemantic(TrendSemantic.UNSPECIFIED_TREND_SEMANTIC)
        .build();
  }

  private KeyFigure getPersonWhoSharedKeysSum(StatisticsJsonStringObject stats) {
    var trend = valueTrendCalculator.getTrend(stats.getPersonsWhoSharedKeys7daysGrowthrate());
    var semantic = valueTrendCalculator.getPositiveTrendGrowth(trend);
    return KeyFigure.newBuilder()
        .setValue(stats.getPersonsWhoSharedKeys7daysSum())
        .setRank(Rank.SECONDARY)
        .setDecimals(0)
        .setTrend(trend)
        .setTrendSemantic(semantic)
        .build();
  }

  @Override
  protected KeyFigureCard buildKeyFigureCard(StatisticsJsonStringObject stats, Builder keyFigureBuilder) {
    return keyFigureBuilder.addAllKeyFigures(
        List.of(
            getPersonWhoSharedKeysDaily(stats),
            getPersonWhoSharedKeysSum(stats),
            getPersonWhoSharedKeysCumulated(stats)))
        .build();
  }


}