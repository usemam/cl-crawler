using System;
using ServiceStack.Redis;
using Usemam.Cl.Crawler.Domain.Model;

namespace Usemam.Cl.Crawler.Domain.Repositories
{
    public class RedisUserRepository : IUserRepository
    {
        private readonly IRedisClientsManager _clientsManager;

        public RedisUserRepository(IRedisClientsManager clientsManager)
        {
            _clientsManager = clientsManager;
        }

        public User GetByEmail(string email)
        {
            using (var client = _clientsManager.GetReadOnlyClient())
            {
                var users = client.As<User>();
                return users.GetValue(email.ToLowerInvariant());
            }
        }

        public void Save(User user)
        {
            using (var client = _clientsManager.GetClient())
            {
                var users = client.As<User>();
                users.SetValue(user.Email.ToLowerInvariant(), user);
            }
        }
    }
}
