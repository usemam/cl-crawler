using ServiceStack;
using ServiceStack.Messaging;
using Usemam.Cl.Crawler.Domain.Messages;
using Usemam.Cl.Crawler.Domain.Repositories;

namespace Usemam.Cl.Crawler.Scheduler.Jobs
{
    public class NotifyParserJob
    {
        public const string JobId = "notify-parser";

        private readonly IMessageService _messageService;
        private readonly IUserRepository _userRepository;

        public NotifyParserJob(IMessageService messageService, IUserRepository userRepository)
        {
            _messageService = messageService;
            _userRepository = userRepository;
        }

        public void Execute()
        {
            var userEmails = _userRepository.GetAllUserEmails();
            using (var messageClient = _messageService.CreateMessageQueueClient())
            {
                foreach (string userEmail in userEmails)
                {
                    messageClient.Publish(new SearchAds { UserEmail = userEmail });
                }
            }
        }
    }
}