package bb.webhook.testutil

import cats.effect.Blocker

final case class TestResources(blocker: Blocker)